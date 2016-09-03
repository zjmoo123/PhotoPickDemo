package com.example.zjm.photopickdemo.dbflow;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by zjm on 16-9-3.
 */
@Migration(version = 2, database = AppDatabase.class)
public class Migration_2_People extends AlterTableMigration<People>{
    public Migration_2_People(Class<People> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        addColumn(SQLiteType.TEXT, People_Table.email.getNameAlias().getName());
    }
}
