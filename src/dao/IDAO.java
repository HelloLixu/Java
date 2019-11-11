/**
 * 文件名：ContractDAO.java
 * 描述：这是所有实体的dao（Data Access Object）接口，定义主要抽象方法包括增删查改。
 * 创建日期：2014-04-24
 * 创建者：李鹏翔
 */

package dao;

import java.util.ArrayList;

import model.*;

public interface IDAO {
	
	/**
	 * 插入一个实体到数据库
	 * @param entity IEntity 被插入的对象
	 * @return succ boolean 函数执行的结果
	 */
	public boolean AddEntity(IEntity entity);
	
	/**
	 * 从数据库删除一个实体
	 * @param entity IEntity 被删除的对象
	 * @return succ boolean 函数执行的结果
	 */
	public boolean DeleteEntity(IEntity entity);
	
	/**
	 * 从数据库更新一个实体
	 * @param entity IEntity 新的的对象，替换掉原来的对象
	 * @return	succ Boolean 函数执行的结果
	 */
	public boolean UpdateEntity(IEntity entity);
	
	/**
	 * 查找一个实体对象
	 * @param id int 被查找的对象的id
	 * @return entity IEntity 如果找到，返回指定的对象，若没有，返回null
	 */
	public IEntity GetOneEntity(int id);
	
	/**
	 * 查找一个实体对象
	 * @param entity IEntity 查找条件
	 * @return entity IEntity 如果找到，返回指定的对象，若没有，返回null
	 */
	public IEntity GetOneEntity(IEntity entity);
	
	/**
	 * 查询指定条件下对象集合
	 * @param entity 查询条件
	 * @return arr ArrayList<IEntity> 查询的结果集
	 */
	public ArrayList<IEntity> GetEntitySet(IEntity entity);
	
	/**
	 * 分页显示时，查询指定页的对象集合
	 * @param entity 查询条件
	 * @param pageNo 指定显示的页号
	 * @param pageRecordNum 指定每页显示的记录数
	 * @return arr ArrayList<IEntity> 查询的结果集
	 */
	public ArrayList<IEntity> GetEntitySet(IEntity entity, int pageNo, int pageRecordNum);
	
	/**
	 * 返回查询条件下的数据总条数
	 * @return int 查询条件下的数据总条数
	 */
	public int getRecordNum();
	
	/**
	 * 返回查询条件下的数据页数
	 * @return int 查询条件下的数据页数
	 */
	public int GetPageNum();
}
