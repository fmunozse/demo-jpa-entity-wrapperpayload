package cloud.fmunozse.demojpaentitywrapperpayload.repository;

import cloud.fmunozse.demojpaentitywrapperpayload.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
