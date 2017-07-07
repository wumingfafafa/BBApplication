package com.deppon.ormlitetest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.deppon.bean.Infor;
import com.deppon.bean.User;
import com.deppon.dao.DaoUtils;
import com.deppon.helper.DatabaseHelper;
import com.deppon.helper.MyDatabaseHelper;
import com.deppon.ormlitetest.DatabaseUtils.MessageShow;
import com.example.ormlitetest.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class MainActivity extends Activity {
	private User user;
	private MyDatabaseHelper helper;
	private DaoUtils<User> userDao;
	private DaoUtils<Infor> inforDao;
	private List<Infor> list;
	private String DB_PATH="/data/data/com.example.ormlitetest/databases/test88.db";
	private String DB_BACKUP_PATH=Environment.getExternalStorageDirectory()+"/feng_contactsBackup";
	private String DATABASE_PATH=Environment.getExternalStorageDirectory() + "/ttttest.db";
	private DatabaseUtils bru;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	   helper = new MyDatabaseHelper(MainActivity.this,DATABASE_PATH);
	   bru=new DatabaseUtils(MainActivity.this);
	   bru.setDatabasePath(helper, DATABASE_PATH,2);

       
	
		TextView tv = (TextView) findViewById(R.id.tv);

		try {
			userDao = new DaoUtils<User>(helper, User.class);
			inforDao = new DaoUtils<Infor>(helper, Infor.class);

			userDao.executeSql("delete from tb_user");

			// infor与user为多对一的关系
			Infor infor = new Infor();
			infor.setDetile("你好啊");

			// 一次插入一条数据
			// User user = new User();
			// user.setName("lisi");
			// user.setDesc("老师");
			// user.setPassword("999999");
			// user.setInfor(infor);
			// userDao.insertData(user);

			// 一次插入多条数据
			List<User> userList = new ArrayList<User>();
			for (int i = 0; i < 500; i++) {
				// 外键对象需要在创建当前引用对象之前创建，并且需要在外键对象所对应的表中插入该外键对象信息记录
				// infor为外键对象，user与infor为一一对应的关系
				// Infor infor = new Infor();
				// infor.setDetile("你好啊");
				// inforDao.insertData(infor);

				user = new User();
				user.setName("zhangsan" + i);
				user.setDesc("xueshen");
				user.setNum("123456"+i);
				user.setSex(1);
				// user.setInfor(infor);
				userList.add(user);
				
			}

			// Long stime= System.currentTimeMillis();
			userDao.insertDatas(userList);
			// Long etime=System.currentTimeMillis();
			// Log.e("ORMLite_time", etime-stime+"");

			// 使用对象更新数据
			// user.setPassword("887777");
			// userDao.updateData(user);

			// 使用sql更新
			// userDao.updataDatabySQL("update tb_user set name='张三' where name like 'zhangsan%'");

			// 在User表中查询全部数据，可得到User表中外键对象相同的全部User数据信息
			// List<User> list = userDao.queryAllData();
			// for (User u : list) {
			// tv.append(u.toString() + "\n");
			// }

			// 在Infor表中查询全部数据 可得到
			// list = inforDao.queryAllData();
			// for (Infor u : list) {
			// Iterator<User> iterator = u.getUsers().iterator();
			// while (iterator.hasNext()) {
			//
			// User data = iterator.next();
			//
			// tv.append(data.toString() + "\n");
			// }
			//
			// }

			// 批量操作
			final List<User> list_infor = userDao.queryAllData();
			List<User> list = new ArrayList<User>();
			Long stime = System.currentTimeMillis();
			userDao.doBatchTasks(new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					// TODO Auto-generated method stub
					for (User u : list_infor) {
						u.setName("lisi");
						userDao.updateData(u);
					}
					return null;
				}
			});

			Long etime = System.currentTimeMillis();
			Log.e("ORMLite_time", etime - stime + "");
			List<User> list_infor2 = userDao.queryAllData();
			Toast.makeText(MainActivity.this,
					list_infor2.size() + "   "+list_infor2.get(0).getNum(),
					Toast.LENGTH_LONG).show();

			// 条件查询
			// Map<String,Object> clause=new HashMap<String,Object>();
			// clause.put("name", "zhangsan0");
			// List<User> users=userDao.queryDataEqByClause(clause);
			// User user=users.get(0);

			// 使用SQL查询
			// List<String[]> datas = userDao
			// .queryDataBySql("select * from tb_user ");
			// for (int i = 0; i < datas.size(); i++) {
			// for (int j = 0; j < datas.get(i).length; j++) {
			// tv.append(datas.get(i)[j] + "  ");
			// }
			// tv.append("\n");
			// }

			// 使用对象删除
			// userDao.delectData(user);

			// 批量删除
			// List<User> list2 = new ArrayList<User>();
			// for (int i = 0; i < list.size(); i++) {
			// user = new User();
			// user.setId(i);
			// list2.add(user);
			// }
			// userDao.delectDatas(list2);

			
			bru.doDataBackUp(DB_PATH, DB_BACKUP_PATH,new MessageShow() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this,"success", Toast.LENGTH_LONG).show();
				}
				
				@Override
				public void onPepare() {
					// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this,"Pepare", Toast.LENGTH_LONG).show();	
				}
				
				@Override
				public void onFail() {
					// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this,"Fail", Toast.LENGTH_LONG).show();	
				}
			});
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("DB_ERROR", "Get bean dao error cause by " + e.getMessage());
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		OpenHelperManager.releaseHelper();
	}
}