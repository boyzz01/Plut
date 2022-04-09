package com.ardeveloper.plut.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "Keranjang".
*/
public class KeranjangDao extends AbstractDao<Keranjang, Void> {

    public static final String TABLENAME = "Keranjang";

    /**
     * Properties of entity Keranjang.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, int.class, "id", false, "ID");
        public final static Property P_id = new Property(1, String.class, "p_id", false, "P_ID");
        public final static Property P_price = new Property(2, int.class, "p_price", false, "P_PRICE");
        public final static Property P_qty = new Property(3, int.class, "p_qty", false, "P_QTY");
    }


    public KeranjangDao(DaoConfig config) {
        super(config);
    }
    
    public KeranjangDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"Keranjang\" (" + //
                "\"ID\" INTEGER NOT NULL ," + // 0: id
                "\"P_ID\" TEXT," + // 1: p_id
                "\"P_PRICE\" INTEGER NOT NULL ," + // 2: p_price
                "\"P_QTY\" INTEGER NOT NULL );"); // 3: p_qty
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"Keranjang\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Keranjang entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String p_id = entity.getP_id();
        if (p_id != null) {
            stmt.bindString(2, p_id);
        }
        stmt.bindLong(3, entity.getP_price());
        stmt.bindLong(4, entity.getP_qty());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Keranjang entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String p_id = entity.getP_id();
        if (p_id != null) {
            stmt.bindString(2, p_id);
        }
        stmt.bindLong(3, entity.getP_price());
        stmt.bindLong(4, entity.getP_qty());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public Keranjang readEntity(Cursor cursor, int offset) {
        Keranjang entity = new Keranjang( //
            cursor.getInt(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // p_id
            cursor.getInt(offset + 2), // p_price
            cursor.getInt(offset + 3) // p_qty
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Keranjang entity, int offset) {
        entity.setId(cursor.getInt(offset + 0));
        entity.setP_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setP_price(cursor.getInt(offset + 2));
        entity.setP_qty(cursor.getInt(offset + 3));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(Keranjang entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(Keranjang entity) {
        return null;
    }

    @Override
    public boolean hasKey(Keranjang entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
