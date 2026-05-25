package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.account.user.registration.AccountUserRegistrationService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class UserRegistrationGrpcService extends UserRegistrationServiceGrpc.UserRegistrationServiceImplBase {

    Logger log = LoggerFactory.getLogger(UserRegistrationGrpcService.class);

    private final AccountUserRegistrationService accountUserRegistrationService;

    private final UserRegistrationMapper userRegistrationMapper;

    public UserRegistrationGrpcService(AccountUserRegistrationService accountUserRegistrationService, UserRegistrationMapper userRegistrationMapper) {
        this.accountUserRegistrationService = accountUserRegistrationService;
        this.userRegistrationMapper = userRegistrationMapper;
    }


    @Override
    public void registerUser(UserRegistrationRequest request, StreamObserver<UserRegistrationResponse> responseObserver) {
        log.info("[registerUser] Received gRPC request for new user registration with email: {}", request.getUser().getEmail());

        accountUserRegistrationService.assignAccountToNewUser(userRegistrationMapper.toDTO(request))
                .map(userRegistrationMapper::toProto)
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("[registerUser] Internal error during user registration via gRPC for email: {}. Error: {}", request.getUser().getEmail(), error.getMessage());
                            responseObserver.onError(io.grpc.Status.INTERNAL
                                    .withDescription("Error during user registration: " + error.getMessage())
                                    .asRuntimeException());
                        }
                );
    }

}
