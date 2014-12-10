package life;

import life.db.DbIterator;
import life.util.Observable;
import org.json.simple.JSONObject;

import java.util.List;

public interface TransactionProcessor extends Observable<List<? extends Transaction>,TransactionProcessor.Event> {

    public static enum Event {
        REMOVED_UNCONFIRMED_TRANSACTIONS,
        ADDED_UNCONFIRMED_TRANSACTIONS,
        ADDED_CONFIRMED_TRANSACTIONS,
        ADDED_DOUBLESPENDING_TRANSACTIONS
    }

    DbIterator<? extends Transaction> getAllUnconfirmedTransactions();

    Transaction getUnconfirmedTransaction(long transactionId);

    void clearUnconfirmedTransactions();

    void broadcast(Transaction transaction) throws LifeException.ValidationException;

    void processPeerTransactions(JSONObject request) throws LifeException.ValidationException;

    Transaction parseTransaction(byte[] bytes) throws LifeException.ValidationException;

    Transaction parseTransaction(JSONObject json) throws LifeException.ValidationException;

    Transaction.Builder newTransactionBuilder(byte[] senderPublicKey, long amountNQT, long feeNQT, short deadline, Attachment attachment);

}
