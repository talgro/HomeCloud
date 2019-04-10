package io.homecloud.homeserver.localFile;

public class LocalFile {

	private int _id;
	private String _name;
	private String _path;
	private String _type;
	
	
	public LocalFile() { }
	
	public LocalFile(int _id, String _name, String _path, String _type) {
		super();
		this._id = _id;
		this._name = _name;
		this._path = _path;
		this._type = _type;
	}
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_path() {
		return _path;
	}
	public void set_path(String _path) {
		this._path = _path;
	}
	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}



	
	
	
	
	
	
	
}
