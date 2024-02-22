package com.swe.sartoria.service;

import com.swe.sartoria.model.Account;
import com.swe.sartoria.model.Role;
import com.swe.sartoria.repository.AccountRepository;
import com.swe.sartoria.repository.CostumerRepository;
import com.swe.sartoria.repository.RoleRepository;
import com.swe.sartoria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private MailService mailService;
    private RoleRepository roleRepository;
    private CostumerRepository costumerRepository;
    private UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          MailService mailService,
                          RoleRepository roleRepository,
                          CostumerRepository costumerRepository,
                          UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.mailService = mailService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.costumerRepository = costumerRepository;
    }


    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    // this method is intended for the superuser
    // normal users will only have access to their own account
    // and to certain methods that are allowed by their role
    public Account updateAccount(Account account, Long id) {
        Account accountToUpdate = accountRepository.findById(id).orElse(null);
        if (accountToUpdate == null) {
            return null;
        }

        accountToUpdate = account;
        accountToUpdate = accountRepository.save(accountToUpdate);
        return accountToUpdate;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
        } else {
            System.out.println("Account not found");
        }
    }

    public List<Account> getAccountsByRole(String roleName) {

        return accountRepository.findByUserRole(roleName);
    }

    public List<Account> getAccountsByCostumerString(String search){
        return accountRepository.searchByCostumerString(search);
    }

    public List<Account> getByUsername(String username){
        return accountRepository.searchByUsername(username);
    }

    public Account addAccount(Account account){
        if (userRepository.existsByUsername(account.getUser().getUsername())){
            return null;
        }
        return accountRepository.save(account);
    }

    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

}
