package com.coinomi.core.network;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Sha256Hash;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

/**
 * @author John L. Jegutanis
 */
final public class AddressStatus {
    final Address address;
    @Nullable final String status;

    HashSet<ServerClient.HistoryTx> historyTransactions = new HashSet<>();
    HashSet<ServerClient.UnspentTx> unspentTransactions = new HashSet<>();
    HashSet<Sha256Hash> allTransactions = new HashSet<>();


    public AddressStatus(Address address, @Nullable String status) {
        this.address = address;
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    @Nullable public String getStatus() {
        return status;
    }

    /**
     * Queue transactions that are going to be fetched
     * @param txs
     */
    public void queueHistoryTransactions(List<ServerClient.HistoryTx> txs) {
        if (historyTransactions == null) {
            //historyTransactions = (HashSet<ServerClient.HistoryTx>) fillTransactions(txs);
        }
    }

    /**
     * Queue transactions that are going to be fetched
     * @param txs
     */
    public void queueUnspentTransactions(List<ServerClient.UnspentTx> txs) {
        if (unspentTransactions == null) {
           //unspentTransactions = (HashSet<ServerClient.UnspentTx>) fillTransactionsUnspent(txs);
        }
    }

    private HashSet<? extends ServerClient.UnspentTx> fillTransactionsUnspent(List<? extends ServerClient.UnspentTx> txs) {
        HashSet<? extends ServerClient.UnspentTx> txSet = Sets.newHashSet(txs);
        for (ServerClient.UnspentTx tx : txs) {
            allTransactions.add(tx.getTxHash());
        }
        return txSet;
    }

    private HashSet<? extends ServerClient.HistoryTx> fillTransactions(List<? extends ServerClient.HistoryTx> txs) {
        HashSet<? extends ServerClient.HistoryTx> txSet = Sets.newHashSet(txs);
        for (ServerClient.HistoryTx tx : txs) {
            allTransactions.add(tx.getTxHash());
        }
        return txSet;
    }

    /**
     * Return true if history and unspent transactions are queued
     */
    public boolean isReady() {
        return historyTransactions != null && unspentTransactions != null;
    }

    /**
     * Get all queued transactions
     */
    public HashSet<Sha256Hash> getAllTransactionHashes() {
        return allTransactions;
    }

    /**
     * Get history transactions info
     */
    public HashSet<ServerClient.HistoryTx> getHistoryTxs() {
        return historyTransactions;
    }

    /**
     * Get unspent transactions info
     */
    public HashSet<ServerClient.UnspentTx> getUnspentTxs() {
        return unspentTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressStatus status1 = (AddressStatus) o;

        if (!address.equals(status1.address)) return false;
        if (status != null ? !status.equals(status1.status) : status1.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "address=" + address +
                ", status='" + status + '\'' +
                '}';
    }
}
