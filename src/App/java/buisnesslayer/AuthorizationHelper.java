package buisnesslayer;

import datalayer.AccountRepository;
import mongodbCollections.Account;

public class AuthorizationHelper {
    AccountRepository accountRepository = new AccountRepository();
    public boolean isLoginExists(String login){
        return accountRepository.getAll().stream().anyMatch(x->x.getLogin().equals(login));
    }
    public boolean validAuth(String log, String pass){
        return accountRepository.getAll().stream().filter(x->x.getLogin().equals(log)).findFirst().stream().anyMatch(x->x.getPassword().equals(pass));
    }
    public Account getUserByLogin(String login){
        return accountRepository.getAll().stream().filter(x->x.getLogin().equals(login)).findFirst().get();
    }
}
