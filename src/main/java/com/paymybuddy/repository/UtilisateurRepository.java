package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Eutilisateur;

public interface UtilisateurRepository extends JpaRepository<Eutilisateur, String> {

}
