package com.projeto.clube_assinatura.repository;

import com.projeto.clube_assinatura.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {



}
