package com.deppon.ormlitetest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.deppon.helper.DatabaseHelper;



/**
 * @ClassName: BackUpAndRecoverUtils
 * @Description: TODO(备份及恢复工具类)
 * @author fenglei015@deppon.com/273219 
 * @date 2015-12-25 上午11:22:22 
 *
 */
public class DatabaseUtils {
	// 当前数据库地址
	private String DB_PATH;
	// 备份后数据库保存地址
	private String DB_BACKUP_PATH;
	private Context context;
	private MessageShow ms;
	// 备份成功状态
	private final int BACKUP_OK = 1;
	// 备份失败状态
	private final int BACKUP_FAIL = -1;
	// 恢复成功状态
	private final int RECOVER_OK = 2;
	// 恢复失败状态
	private final int RECOVER_FAIL = -2;

	public interface MessageShow{

		public void onPepare();
		public void onSuccess();
		public void onFail();


	}

	public DatabaseUtils(Context context) {
		this.context = context;
	}

	/**
	 * @Title: doDataBackUp
	 * @Description: TODO(数据备份)
	 * @param
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public void doDataBackUp(String DB_PATH, String DB_BACKUP_PATH,MessageShow ms) {
		this.DB_PATH = DB_PATH;
		this.DB_BACKUP_PATH = DB_BACKUP_PATH;
		this.ms=ms;
		new BackUpTask().execute();
	}

	/**
	 * @Title: doDataRecover
	 * @Description: TODO(数据恢复)
	 * @param
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public void doDataRecover(String DB_PATH, String DB_BACKUP_PATH,MessageShow ms) {
		this.DB_PATH = DB_PATH;
		this.DB_BACKUP_PATH = DB_BACKUP_PATH;
		this.ms=ms;
		new RecoverTask().execute();
	}


	/**
	 * @Title: setDatabasePath
	 * @Description: TODO(设置数据库文件保存位置)
	 * @param @param helper
	 * @param @param DATABASE_PATH    设定文件
	 * @return void    返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public void setDatabasePath(DatabaseHelper helper,String DATABASE_PATH,int newVersionCode){
		File f = new File(DATABASE_PATH);
		if (!f.exists()) {
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
					DATABASE_PATH,null);
			db.setVersion(newVersionCode);
			helper.onCreate(db);
		}else{
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
					DATABASE_PATH,null);
			int oldVersionCode=db.getVersion();
			if(newVersionCode>oldVersionCode){
				db.setVersion(newVersionCode);
				helper.onUpgrade(db, oldVersionCode, newVersionCode);
			}
		}
	}

	/**
	 * @ClassName: BackUpTask
	 * @Description: TODO(数据库备份异步任务)
	 * @author fenglei015@deppon.com/273219
	 * @date 2015-12-25 上午11:10:58
	 *
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	class BackUpTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(ms!=null) {
				ms.onPepare();
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub

			// 默认路径 /data/data/(包名)/databases/*.db
			File dbFile = context.getDatabasePath(DB_PATH);
			File exportDir = new File(DB_BACKUP_PATH);
			int result = 0;
			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}
			File backup = new File(exportDir, dbFile.getName());
			try {
				backup.createNewFile();
				fileCopy(dbFile, backup);
				result = BACKUP_OK;
			} catch (Exception e) {
				Log.e("backup_error", e.getMessage());
				result = BACKUP_FAIL;
			}

			return result;

		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			switch (result) {
				case BACKUP_OK:
					if(ms!=null) {
						ms.onSuccess();
					}
					break;

				case BACKUP_FAIL:
					if(ms!=null) {
						ms.onFail();
					}
					break;
			}
		}

	}

	/**
	 * @ClassName: RecoverTask
	 * @Description: TODO(数据库恢复异步任务)
	 * @author fenglei015@deppon.com/273219
	 * @date 2015-12-25 上午11:10:29
	 *
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	class RecoverTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(ms!=null) {
				ms.onPepare();
			}
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int result = 0;
			File dbFile = context.getDatabasePath(DB_PATH);
			File exportDir = new File(DB_BACKUP_PATH);
			File backup = new File(exportDir, dbFile.getName());

			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}
			try {
				fileCopy(backup, dbFile);
				result = RECOVER_OK;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("recover_error", e.getMessage());
				result = RECOVER_FAIL;
			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			switch (result) {
				case RECOVER_OK:
					if(ms!=null) {
						ms.onSuccess();
					}
					break;

				case RECOVER_FAIL:
					if(ms!=null) {
						ms.onFail();
					}
					break;
			}
		}

	}

	/**
	 * @Title: fileCopy
	 * @Description: TODO(文件拷贝方法)
	 * @param @param dbFile
	 * @param @param backup
	 * @param @throws IOException 设定文件
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	private void fileCopy(File dbFile, File backup) throws IOException {
		// TODO Auto-generated method stub
		FileChannel inChannel = new FileInputStream(dbFile).getChannel();
		FileChannel outChannel = new FileOutputStream(backup).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("fileCopy_error", e.getMessage());
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}
}
