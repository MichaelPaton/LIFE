package life.http;

import life.Account;
import life.Attachment;
import life.LifeException;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class SendMessage extends CreateTransaction {

    static final SendMessage instance = new SendMessage();

    private SendMessage() {
        super(new APITag[] {APITag.MESSAGES, APITag.CREATE_TRANSACTION}, "recipient");
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) throws LifeException {
        long recipient = ParameterParser.getRecipientId(req);
        Account account = ParameterParser.getSenderAccount(req);
        return createTransaction(req, account, recipient, 0, Attachment.ARBITRARY_MESSAGE);
    }

}
