package life.http;

import life.Account;
import life.Generator;
import life.LifeCoin;
import life.crypto.Crypto;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static life.http.JSONResponses.MISSING_SECRET_PHRASE;
import static life.http.JSONResponses.NOT_FORGING;
import static life.http.JSONResponses.UNKNOWN_ACCOUNT;


public final class GetForging extends APIServlet.APIRequestHandler {

    static final GetForging instance = new GetForging();

    private GetForging() {
        super(new APITag[] {APITag.FORGING}, "secretPhrase");
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) {

        String secretPhrase = req.getParameter("secretPhrase");
        if (secretPhrase == null) {
            return MISSING_SECRET_PHRASE;
        }
        Account account = Account.getAccount(Crypto.getPublicKey(secretPhrase));
        if (account == null) {
            return UNKNOWN_ACCOUNT;
        }

        Generator generator = Generator.getGenerator(secretPhrase);
        if (generator == null) {
            return NOT_FORGING;
        }

        JSONObject response = new JSONObject();
        long deadline = generator.getDeadline();
        response.put("deadline", deadline);
        response.put("hitTime", generator.getHitTime());
        int elapsedTime = LifeCoin.getEpochTime() - LifeCoin.getBlockchain().getLastBlock().getTimestamp();
        response.put("remaining", Math.max(deadline - elapsedTime, 0));
        return response;

    }

    @Override
    boolean requirePost() {
        return true;
    }

}
