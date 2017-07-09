package com.deppon.dao;

import com.deppon.helper.DatabaseHelper;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @ClassName: DaoUtils
 * @Description: TODO(数据表操作类)
 * @author fenglei015@deppon.com/273219
 * @date 2015-12-14 下午3:21:25
 * 
 * @param <T>
 */
public class DaoUtils<T> {
	private Dao<T, Integer> dao;
	private DatabaseHelper helper;
	private DatabaseConnection connection;
	private Savepoint savePoint;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:返回实体类对应的dao对象
	 * </p>
	 * 
	 * @param helper
	 * @param beanClass
	 * @throws SQLException
	 * @author fenglei015@deppon.com/273219
	 * @date 2015-12-16 下午4:03:23
	 */
	public DaoUtils(DatabaseHelper helper, Class<T> beanClass)
			throws SQLException {
		this.helper=helper;
		if (dao == null) {
			dao = helper.getDao(beanClass);
		}

	}

	public Dao<T, Integer> getDao() {
		return dao;
	}

	public void setDao(Dao<T, Integer> dao) {
		this.dao = dao;
	}
/*
	 * *************新增数据方法*********************************
	 */

	/**
	 * @Title: insertData
	 * @Description: TODO(新增单条数据)
	 * @param @param object
	 * @param @throws SQLException 设定文件
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public void insertData(T object) throws SQLException {
		dao.createIfNotExists(object);
	}


	/**
	 * @Title: insertDatas
	 * @Description: TODO(批量新增)
	 * @param @param collection
	 * @param @throws SQLException 设定文件
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public void insertDatas(Collection<T> collection) throws SQLException {
		dao.create(collection);

	}

	/*
	 * *************查询数据方法*********************************
	 */

	/**
	 * @throws IOException
	 * @Title: queryAllData
	 * @Description: TODO(使用迭代器查询表中所用记录)
	 * @param @return
	 * @param @throws SQLException 设定文件
	 * @return List<T> 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public List<T> queryAllData() throws IOException {
		List<T> datalist = new ArrayList<T>();
		CloseableIterator<T> iterator = dao.closeableIterator();

		try {

			while (iterator.hasNext()) {

				T data = iterator.next();

				datalist.add(data);

			}

		} finally {

			// close it at the end to close underlying SQL statement

			iterator.close();

		}
		return datalist;
	}

	/**
	 * @Title: queryDataByClause
	 * @Description: TODO(根据条件查询)
	 * @param @return
	 * @param @throws SQLException 设定文件
	 * @return Where 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public List<T> queryDataEqByClause(Map<String, Object> clause)
			throws SQLException {

		// queryBuild构建多条件查询
		List<T> result = dao.queryForFieldValuesArgs(clause);
		return result;
	}

	/**
	 * @Title: queryDataBySql
	 * @Description: TODO(根据sql查询记录)
	 * @param @param sql 查询sql语句
	 * @param @return
	 * @param @throws SQLException 设定文件
	 * @return List<String[]> 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public List<String[]> queryDataBySql(String sql) throws SQLException {
		GenericRawResults<String[]> rawResults = dao.queryRaw(sql);
		List<String[]> results = rawResults.getResults();
		return results;
	}

	/*
	 * *************更新数据方法*********************************
	 */

	/**
	 * @Title: updateData
	 * @Description: TODO(使用对象更新一条记录)
	 * @param @param object 需要更新的对象
	 * @param @throws SQLException 设定文件
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public void updateData(T object) throws SQLException {
		dao.update(object);
	}
	
	

	/**
	 * @Title: updataDatabySQL
	 * @Description: TODO(根据条件做update时直接使用sql语句进行更新)
	 * @param @param statement 更新的SQL语句必须包含关键字INSERT,、DELETE、 UPDATE
	 * @param @param arguments
	 * @param @throws SQLException 设定文件
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public void updataDatabySQL(String sql) throws SQLException {
		dao.updateRaw(sql);
	}

	/*
	 * *************删除数据方法*********************************
	 */

	/**
	 * @Title: delectData
	 * @Description: TODO(使用对象删除一条记录)
	 * @param @param arg0
	 * @param @throws SQLException 设定文件
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public void delectData(T object) throws SQLException {
		dao.delete(object);
	}

	/**
	 * @Title: delectDatas
	 * @Description: TODO(批量删除)
	 * @param @param datas 对象的集合
	 * @param @throws SQLException 设定文件
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public void delectDatas(Collection<T> datas) throws SQLException {
		dao.delete(datas);
	}
	
    /** 
     * @Title: doBatchTasks 
     * @Description: TODO(可进行批量操作，需要进行批量操作时直接将代码放到callable的call()中即可) 
     * @param @param callable
     * @param @throws Exception    设定文件 
     * @return void    返回类型 
     * @author fenglei015@deppon.com/273219 
     * @throws 
     */ 
    public <A> void doBatchTasks(Callable<A> callable) throws Exception{
    	dao.callBatchTasks(callable);
    }

	/*
	 * *************直接执行sql语句方法*********************************
	 */

	/**
	 * @Title: executeSql
	 * @Description: TODO(直接执行所有的sql语句，应用于特殊场景)
	 * @param @param sql sql语句
	 * @param @throws SQLException 设定文件
	 * @return void 返回类型
	 * @author fenglei015@deppon.com/273219
	 * @throws
	 */
	public int executeSql(String sql) throws SQLException {
		int result = dao.executeRaw(sql);
		return result;
	}
	
	/*
	 * *************事务操作*********************************
	 */

	/** 
	 * @Title: beginTransaction 
	 * @Description: TODO(开启数据库事务操作) 
	 * @param @param savepoint
	 * @param @throws SQLException    设定文件 
	 * @return void    返回类型 
	 * @author fenglei015@deppon.com/273219 
	 * @throws 
	 */ 
	public void beginTransaction(String savepoint) throws SQLException{
		connection=dao.startThreadConnection();
		savePoint =connection.setSavePoint(savepoint);
		
	}
		
	/** 
	 * @Title: commit 
	 * @Description: TODO(提交事务) 
	 * @param @throws SQLException    设定文件 
	 * @return void    返回类型 
	 * @author fenglei015@deppon.com/273219 
	 * @throws 
	 */ 
	public void commit() throws SQLException{
       connection.commit(savePoint);
       dao.endThreadConnection(connection);
	}
	
	/** 
	 * @Title: rollBack 
	 * @Description: TODO(事务回滚) 
	 * @param @param savepoint
	 * @param @throws SQLException    设定文件 
	 * @return void    返回类型 
	 * @author fenglei015@deppon.com/273219 
	 * @throws 
	 */ 
	public void rollBack(Savepoint savepoint) throws SQLException{
	  connection.rollback(savepoint);
	  dao.endThreadConnection(connection);
	}
}
