package life.http;

import life.DigitalGoodsStore;
import life.LifeException;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class GetDGSGoodsPurchaseCount extends APIServlet.APIRequestHandler {

    static final GetDGSGoodsPurchaseCount instance = new GetDGSGoodsPurchaseCount();

    private GetDGSGoodsPurchaseCount() {
        super(new APITag[] {APITag.DGS}, "goods", "withPublicFeedbacksOnly");
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) throws LifeException {

        DigitalGoodsStore.Goods goods = ParameterParser.getGoods(req);
        final boolean withPublicFeedbacksOnly = "true".equalsIgnoreCase(req.getParameter("withPublicFeedbacksOnly"));

        JSONObject response = new JSONObject();
        response.put("numberOfPurchases", DigitalGoodsStore.getGoodsPurchaseCount(goods.getId(), withPublicFeedbacksOnly));
        return response;

    }

}
