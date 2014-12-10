package life.http;

import life.Account;
import life.Alias;
import life.Asset;
import life.AssetTransfer;
import life.Block;
import life.DigitalGoodsStore;
import life.Generator;
import life.LifeCoin;
import life.Order;
import life.Trade;
import life.peer.Peer;
import life.peer.Peers;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class GetState extends APIServlet.APIRequestHandler {

    static final GetState instance = new GetState();

    private GetState() {
        super(new APITag[] {APITag.INFO}, "includeCounts");
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) {

        JSONObject response = new JSONObject();

        response.put("application", LifeCoin.APPLICATION);
        response.put("version", LifeCoin.VERSION);
        response.put("time", LifeCoin.getEpochTime());
        Block lastBlock = LifeCoin.getBlockchain().getLastBlock();
        response.put("lastBlock", lastBlock.getStringId());
        response.put("cumulativeDifficulty", lastBlock.getCumulativeDifficulty().toString());
        response.put("numberOfBlocks", lastBlock.getHeight() + 1);

        /*
        long totalEffectiveBalance = 0;
        try (DbIterator<Account> accounts = Account.getAllAccounts(0, -1)) {
            for (Account account : accounts) {
                long effectiveBalanceLIFE = account.getEffectiveBalanceLIFE();
                if (effectiveBalanceLIFE > 0) {
                    totalEffectiveBalance += effectiveBalanceLIFE;
                }
            }
        }
        response.put("totalEffectiveBalanceLIFE", totalEffectiveBalance);
        */

        if (!"false".equalsIgnoreCase(req.getParameter("includeCounts"))) {
            response.put("numberOfTransactions", LifeCoin.getBlockchain().getTransactionCount());
            response.put("numberOfAccounts", Account.getCount());
            response.put("numberOfAssets", Asset.getCount());
            int askCount = Order.Ask.getCount();
            int bidCount = Order.Bid.getCount();
            response.put("numberOfOrders", askCount + bidCount);
            response.put("numberOfAskOrders", askCount);
            response.put("numberOfBidOrders", bidCount);
            response.put("numberOfTrades", Trade.getCount());
            response.put("numberOfTransfers", AssetTransfer.getCount());
            response.put("numberOfAliases", Alias.getCount());
            response.put("numberOfGoods", DigitalGoodsStore.Goods.getCount());
            response.put("numberOfPurchases", DigitalGoodsStore.Purchase.getCount());
            response.put("numberOfTags", DigitalGoodsStore.Tag.getCount());
            //response.put("numberOfPolls", Poll.getCount());
            //response.put("numberOfVotes", Vote.getCount());
        }

        response.put("numberOfPeers", Peers.getAllPeers().size());
        response.put("numberOfUnlockedAccounts", Generator.getAllGenerators().size());
        Peer lastBlockchainFeeder = LifeCoin.getBlockchainProcessor().getLastBlockchainFeeder();
        response.put("lastBlockchainFeeder", lastBlockchainFeeder == null ? null : lastBlockchainFeeder.getAnnouncedAddress());
        response.put("lastBlockchainFeederHeight", LifeCoin.getBlockchainProcessor().getLastBlockchainFeederHeight());
        response.put("isScanning", LifeCoin.getBlockchainProcessor().isScanning());
        response.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        response.put("maxMemory", Runtime.getRuntime().maxMemory());
        response.put("totalMemory", Runtime.getRuntime().totalMemory());
        response.put("freeMemory", Runtime.getRuntime().freeMemory());

        return response;
    }

}
