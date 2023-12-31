package com.ts.postmaster.dao.repository;

import com.ts.postmaster.dao.model.PMUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author toyewole
 */
@Repository
public interface IPMUserRepository extends JpaRepository<PMUser, Long> {

    @Query("Select pm from PMUser pm where pm.username =:username or pm.email=:username")
    Optional<PMUser> findPMUserByEmailOrUsername(String username);

    @Query("select pm from PMUser pm  where pm.username =:username or pm.email=:username ")
    UserDetails getUserDetailByEmail(String username);
    @Query("select pm from PMUser pm  where pm.username =:username or pm.email=:username ")
    PMUser getPMUserByUsername(String username);

    boolean existsPMUserByEmail(String email);

    boolean existsPMUserByUsername(String username);
}

