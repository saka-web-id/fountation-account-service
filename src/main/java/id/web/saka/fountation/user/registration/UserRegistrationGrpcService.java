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
        log.info("Received gRPC request for user registration: email={}", request.getUser().getEmail());

        accountUserRegistrationService.assignAccountToNewUser(userRegistrationMapper.toDTO(request))
                .map(userRegistrationMapper::toProto)
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("Error during user registration via gRPC", error);
                            responseObserver.onError(io.grpc.Status.INTERNAL
                                    .withDescription("Error during user registration: " + error.getMessage())
                                    .asRuntimeException());
                        }
                );
    }

}
