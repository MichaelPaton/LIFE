package life;

import life.db.BasicDb;
import life.db.TransactionalDb;

public final class Db {

    public static final TransactionalDb db = new TransactionalDb(new BasicDb.DbProperties()
            .maxCacheSize(LifeCoin.getIntProperty("life.dbCacheKB"))
            .dbUrl(Constants.isTestnet ? LifeCoin.getStringProperty("life.testDbUrl") : LifeCoin.getStringProperty("life.dbUrl"))
            .maxConnections(LifeCoin.getIntProperty("life.maxDbConnections"))
            .loginTimeout(LifeCoin.getIntProperty("life.dbLoginTimeout"))
            .defaultLockTimeout(LifeCoin.getIntProperty("life.dbDefaultLockTimeout") * 1000)
    );

    /*
    public static final BasicDb userDb = new BasicDb(new BasicDb.DbProperties()
            .maxCacheSize(LifeCoin.getIntProperty("life.userDbCacheKB"))
            .dbUrl(Constants.isTestnet ? LifeCoin.getStringProperty("life.testUserDbUrl") : LifeCoin.getStringProperty("life.userDbUrl"))
            .maxConnections(LifeCoin.getIntProperty("life.maxUserDbConnections"))
            .loginTimeout(LifeCoin.getIntProperty("life.userDbLoginTimeout"))
            .defaultLockTimeout(LifeCoin.getIntProperty("life.userDbDefaultLockTimeout") * 1000)
    );
    */

    static void init() {
        db.init("sa", "sa", new LifeDbVersion());
        //userDb.init("sa", "databaseencryptionpassword sa", new UserDbVersion());
    }

    static void shutdown() {
        //userDb.shutdown();
        db.shutdown();
    }

    private Db() {} // never

}
