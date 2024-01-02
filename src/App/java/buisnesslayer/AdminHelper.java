package buisnesslayer;

import datalayer.AccountRepository;

public class AdminHelper {
    AccountRepository accountRepository = new AccountRepository();
    public boolean CheckUserForDelete(String login){
        return accountRepository.getAll().stream().anyMatch(x -> x.getLogin().equals(login));
    }
    public boolean CheckUserForAdd(String login){
        return accountRepository.getAll().stream().noneMatch(x -> x.getLogin().equals(login));
    }
}
