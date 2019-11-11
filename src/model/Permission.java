package model;

public class Permission implements IEntity {


		private int id;
		private String code;
		private String name;
		private String description;
		
		public Permission(){
			this.id=0;
			this.code=null;
			this.name=null;
			this.description=null;
		}
		
		public Permission(int id,String code,String name,String description){
			this.id=id;
			this.code=code;
			this.name=name;
			this.description=description;
		
		}
		public int GetId(){
		   return id;
		}
		public String GetCode(){
			return code;
		}
		public String GetName(){
			return name;
		}
		public String GetDescription(){
			return description;
		}
		public void SetId(int id){
			this.id=id;
		}
		public void SetCode(String code){
			this.code=code;
		}
		public void SetName(String name){
			this.name=name;
		}
		public void SetDescription(String description){
			this.description=description;
		}
	}

