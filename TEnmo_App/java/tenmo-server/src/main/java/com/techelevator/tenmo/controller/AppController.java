package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.exceptions.InsufficientFunds;
import com.techelevator.tenmo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AppController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TransferDao transferDao;

    @Autowired
    TransferStatusDao transferStatusDao;

    @Autowired
    TransferTypeDao transferTypeDao;

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Balance obtainBalance(Principal principal) {

        String name = principal.getName();
        int userId = userDao.findIdByUsername(name);
        BigDecimal balance = accountDao.retrieveBalance(userId);

        Balance balanceObject = new Balance();
        balanceObject.setBalance(balance);

        return balanceObject;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path="/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @RequestMapping(path="/users/{id}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable int id) {
        return userDao.getUserByUserId(id);
    }

    @RequestMapping(path="/account/{id}")
    public Account getAccountByAccountId(@PathVariable int id) {
        return accountDao.getAccountByAccountId(id);
    }

    @RequestMapping(path="/account/user/{id}")
    public Account getAccountByUserId(@PathVariable int id) {
        return accountDao.getAccountByUserId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/transfers/", method = RequestMethod.POST)
    public void addTransfer(@RequestBody Transfer transfer) throws InsufficientFunds {
        System.out.println(transfer);
        BigDecimal amountToTransfer = transfer.getTransferAmount();
        Account accountFrom = accountDao.getAccountByAccountId(transfer.getAccountFrom());
        Account accountTo = accountDao.getAccountByAccountId(transfer.getAccountTo());

        accountFrom.getBalance().sendMoney(amountToTransfer);
        accountTo.getBalance().receiveMoney(amountToTransfer);

        transferDao.createTransfer(transfer);
        transferDao.updateTransfer(transfer);

        accountDao.updateAccount(accountFrom);
        accountDao.updateAccount(accountTo);

    }

    @RequestMapping(path="/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int id) {
        return transferDao.getTransferByTransferId(id);
    }

    @RequestMapping(path="/transfers/user/{userId}", method = RequestMethod.GET)
    public List<Transfer> getTransferByUserId(@PathVariable int userId) {
        return transferDao.getTransferByUserId(userId);
    }

    @RequestMapping(path="/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers() {

        return transferDao.getAllTransfers();
    }

    @RequestMapping(path = "transferstatus/filter", method = RequestMethod.GET)
    public TransferStatus getTransferStatusByDescription(@RequestParam String description) {
        return transferStatusDao.getTransferStatusDescription(description);
    }

    @RequestMapping(path = "transferstatus/{id}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusById(@PathVariable int id) {
        return transferStatusDao.getTransferStatusId(id);
    }

    @RequestMapping(path = "transfertype/filter", method = RequestMethod.GET)
    public TransferType getTransferTypeByDescription(@RequestParam String description) {
        return transferTypeDao.getTransferTypeDescription(description);
    }

    @RequestMapping(path = "transfertype/{id}", method = RequestMethod.GET)
    public TransferType getTransferTypeById(@PathVariable int id) {

        return transferTypeDao.getTransferTypeId(id);
    }
}


