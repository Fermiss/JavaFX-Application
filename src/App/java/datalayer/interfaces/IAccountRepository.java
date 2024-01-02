package datalayer.interfaces;

import mongodbCollections.Account;

import java.util.List;

public interface IAccountRepository {
    void add(Account account);
    void delete(Account account);
    void update(Account account);
    List<Account> getAll();
}
