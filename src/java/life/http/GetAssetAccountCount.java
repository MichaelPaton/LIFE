package life.http;

import life.Account;
import life.Asset;
import life.LifeException;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class GetAssetAccountCount extends APIServlet.APIRequestHandler {

    static final GetAssetAccountCount instance = new GetAssetAccountCount();

    private GetAssetAccountCount() {
        super(new APITag[] {APITag.AE}, "asset", "height");
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) throws LifeException {

        Asset asset = ParameterParser.getAsset(req);
        int height = ParameterParser.getHeight(req);

        JSONObject response = new JSONObject();
        response.put("numberOfAccounts", Account.getAssetAccountCount(asset.getId(), height));
        return response;

    }

}
