package life.http;

import life.Asset;
import life.util.Convert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static life.http.JSONResponses.INCORRECT_ASSET;
import static life.http.JSONResponses.UNKNOWN_ASSET;

public final class GetAssets extends APIServlet.APIRequestHandler {

    static final GetAssets instance = new GetAssets();

    private GetAssets() {
        super(new APITag[] {APITag.AE}, "assets", "assets", "assets", "includeCounts"); // limit to 3 for testing
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) {

        String[] assets = req.getParameterValues("assets");
        boolean includeCounts = !"false".equalsIgnoreCase(req.getParameter("includeCounts"));

        JSONObject response = new JSONObject();
        JSONArray assetsJSONArray = new JSONArray();
        response.put("assets", assetsJSONArray);
        for (String assetIdString : assets) {
            if (assetIdString == null || assetIdString.equals("")) {
                continue;
            }
            try {
                Asset asset = Asset.getAsset(Convert.parseUnsignedLong(assetIdString));
                if (asset == null) {
                    return UNKNOWN_ASSET;
                }
                assetsJSONArray.add(JSONData.asset(asset, includeCounts));
            } catch (RuntimeException e) {
                return INCORRECT_ASSET;
            }
        }
        return response;
    }

}
