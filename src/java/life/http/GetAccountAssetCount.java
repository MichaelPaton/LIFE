package life.http;

import life.Account;
import life.LifeException;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class GetAccountAssetCount extends APIServlet.APIRequestHandler {

    static final GetAccountAssetCount instance = new GetAccountAssetCount();

    private GetAccountAssetCount() {
        super(new APITag[] {APITag.ACCOUNTS, APITag.AE}, "account", "height");
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) throws LifeException {

        Account account = ParameterParser.getAccount(req);
        int height = ParameterParser.getHeight(req);

        JSONObject response = new JSONObject();
        response.put("numberOfAssets", Account.getAccountAssetCount(account.getId(), height));
        return response;
    }

}
