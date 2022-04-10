package com.ardeveloper.plut.data.service;

import android.app.Activity;

import com.ardeveloper.plut.MainApp;
import com.ardeveloper.plut.data.db.DaoSession;
import com.ardeveloper.plut.data.db.Keranjang;
import com.ardeveloper.plut.data.db.KeranjangDao;
import com.ardeveloper.plut.data.db.Product;
import com.ardeveloper.plut.data.db.ProductDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class AllDbService {
    Activity activity;

    public AllDbService(Activity activity) {
        this.activity = activity;
    }

    public int addTocart(String id_p){
        DaoSession daoSession = ((MainApp) activity.getApplication()).getDaoSession();
        KeranjangDao keranjangDao = daoSession.getKeranjangDao();
        QueryBuilder<Keranjang> qb = keranjangDao.queryBuilder();
        qb.where(KeranjangDao.Properties.P_id.eq(id_p));
        if (qb.list().size() > 0){
            return 2;
        }else {
            Keranjang keranjang = new Keranjang();
            keranjang.p_id = id_p;
            daoSession.getKeranjangDao().insert(keranjang);
            return 1;
        }

    }

    public void addToProduct(List<Product> productList){
        DaoSession daoSession = ((MainApp) activity.getApplication()).getDaoSession();
        ProductDao dao = daoSession.getProductDao();
        dao.deleteAll();
        dao.insertOrReplaceInTx(productList);
    }
}
