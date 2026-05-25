package id.web.saka.fountation.authorization.policy.client;

import id.web.saka.fountation.authorization.policy.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("policyGrpcClient")
public class PolicyGrpcClientImpl implements PolicyClient {

    private static final Logger logger = LoggerFactory.getLogger(PolicyGrpcClientImpl.class);
    private final PolicyGrpcMapper mapper;

    @GrpcClient("fountation-authorization-service")
    private PolicyServiceGrpc.PolicyServiceStub policyServiceStub;

    public PolicyGrpcClientImpl(PolicyGrpcMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mono<PolicyResponseDTO> evaluate(Long userId, Long companyId, PolicyRequestDTO authRequest) {
        logger.info("[evaluate] Evaluating policy via gRPC for user ID: {} in company ID: {} on path: {}", userId, companyId, authRequest.resource());

        PolicyRequest.Builder requestBuilder = mapper.toProto(authRequest).toBuilder()
                .setCompanyId(companyId);

        if (userId != null) {
            requestBuilder.setUserId(userId);
        }

        PolicyRequest request = requestBuilder.build();

        return Mono.create(sink -> {
            policyServiceStub.checkPolicy(request, new StreamObserver<PolicyResponse>() {
                @Override
                public void onNext(PolicyResponse response) {
                    PolicyResponseDTO responseDTO = mapper.toDTO(response);
                    logger.info("[evaluate] Successfully received gRPC policy evaluation response: isAllowed={}", responseDTO.isAllow());
                    sink.success(responseDTO);
                }

                @Override
                public void onError(Throwable t) {
                    logger.error("[evaluate] Failed to evaluate policy via gRPC for user ID: {}. Error: {}", userId, t.getMessage());
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    // Completed
                }
            });
        });
    }
}
