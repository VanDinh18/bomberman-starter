package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {

	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		System.out.println("kk");
		if(e instanceof Bomber){

			((Bomber) e).addPowerup(this);
			remove();
			return true;
		}
		return false;
	}

	//TODO: viet them
	@Override
	public void setValues() {
		_active = true;
		Game.addBombRadius(1);
	}
}
