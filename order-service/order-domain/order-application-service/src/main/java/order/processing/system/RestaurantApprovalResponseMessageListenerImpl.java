package order.processing.system;


import lombok.extern.slf4j.Slf4j;
import order.processing.system.dto.message.RestaurantApprovalResponse;
import order.processing.system.ports.input.listener.RestaurantApprovalResponseMessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {
    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

    }
}
