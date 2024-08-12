package order.processing.system;

import lombok.extern.slf4j.Slf4j;
import order.processing.system.dto.message.PaymentResponse;
import order.processing.system.ports.input.listener.PaymentResponseMessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCanceled(PaymentResponse paymentResponse) {

    }
}
