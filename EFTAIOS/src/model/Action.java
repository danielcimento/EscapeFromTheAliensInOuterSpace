package model;

public interface Action {
	public void perform();
	public boolean isVisibleToPlayer(Player p);
}
