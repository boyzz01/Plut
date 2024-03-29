package com.ardeveloper.plut.data.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ardeveloper.plut.data.db.Kategori;
import com.ardeveloper.plut.data.db.Keranjang;
import com.ardeveloper.plut.data.db.Kota;
import com.ardeveloper.plut.data.db.Product;
import com.ardeveloper.plut.data.db.User;

import com.ardeveloper.plut.data.db.KategoriDao;
import com.ardeveloper.plut.data.db.KeranjangDao;
import com.ardeveloper.plut.data.db.KotaDao;
import com.ardeveloper.plut.data.db.ProductDao;
import com.ardeveloper.plut.data.db.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig kategoriDaoConfig;
    private final DaoConfig keranjangDaoConfig;
    private final DaoConfig kotaDaoConfig;
    private final DaoConfig productDaoConfig;
    private final DaoConfig userDaoConfig;

    private final KategoriDao kategoriDao;
    private final KeranjangDao keranjangDao;
    private final KotaDao kotaDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        kategoriDaoConfig = daoConfigMap.get(KategoriDao.class).clone();
        kategoriDaoConfig.initIdentityScope(type);

        keranjangDaoConfig = daoConfigMap.get(KeranjangDao.class).clone();
        keranjangDaoConfig.initIdentityScope(type);

        kotaDaoConfig = daoConfigMap.get(KotaDao.class).clone();
        kotaDaoConfig.initIdentityScope(type);

        productDaoConfig = daoConfigMap.get(ProductDao.class).clone();
        productDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        kategoriDao = new KategoriDao(kategoriDaoConfig, this);
        keranjangDao = new KeranjangDao(keranjangDaoConfig, this);
        kotaDao = new KotaDao(kotaDaoConfig, this);
        productDao = new ProductDao(productDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Kategori.class, kategoriDao);
        registerDao(Keranjang.class, keranjangDao);
        registerDao(Kota.class, kotaDao);
        registerDao(Product.class, productDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        kategoriDaoConfig.clearIdentityScope();
        keranjangDaoConfig.clearIdentityScope();
        kotaDaoConfig.clearIdentityScope();
        productDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public KategoriDao getKategoriDao() {
        return kategoriDao;
    }

    public KeranjangDao getKeranjangDao() {
        return keranjangDao;
    }

    public KotaDao getKotaDao() {
        return kotaDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
