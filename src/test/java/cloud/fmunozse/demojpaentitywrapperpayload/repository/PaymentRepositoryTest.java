package cloud.fmunozse.demojpaentitywrapperpayload.repository;

import cloud.fmunozse.demojpaentitywrapperpayload.model.Payment;
import cloud.fmunozse.demojpaentitywrapperpayload.model.iso2022.ISOCreditTransferTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentRepositoryTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void whenInsertPaymentTypeCreditTransfer_thenCouldBeSearch() {

        ISOCreditTransferTransaction trn = new ISOCreditTransferTransaction();
        trn.setCreditPartyAgentId("CreditPartyAgentId");
        Payment payment = new Payment();
        payment.setName("test-name");
        payment.setPayloadTyped(ISOCreditTransferTransaction.class, trn);

        payment = paymentRepository.saveAndFlush(payment);
        payment = paymentRepository.findOne(payment.getId());

        assertThat(payment.getName(), is("test-name"));
        assertThat( payment.getPayloadTyped(ISOCreditTransferTransaction.class).getCreditPartyAgentId() ,   is("CreditPartyAgentId"));
    }


    /*
    @Test
    public void whenInsertPaymentTypeCreditTransferAndReturn_thenCouldBeSearchBoth() {

        ISOCreditTransferTransaction trnCreditTransfer = new ISOCreditTransferTransaction();
        trnCreditTransfer.setCreditPartyAgentId("CreditPartyAgentId");
        Payment payment = new Payment();
        payment.setName("test-name");
        payment.setPayloadTyped(trnCreditTransfer);

        payment = paymentRepository.saveAndFlush(payment);
        payment = paymentRepository.findOne(payment.getId());

        assertThat(payment.getName(), is("test-name"));
        assertThat( payment.getPayloadTyped(ISOCreditTransferTransaction.class).getCreditPartyAgentId() ,   is("CreditPartyAgentId"));
    }
    */


}