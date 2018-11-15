package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static String [][] _map = new String[50][50];

	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) throws LoadLevelException{
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		try {
			URL absPath = FileLevelLoader.class.getResource("/Levels/Level" + level + ".txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(absPath.openStream()));

			String data = in.readLine();
			StringTokenizer tokens = new StringTokenizer(data);
			set_level(Integer.parseInt(tokens.nextToken()));
			set_height(Integer.parseInt(tokens.nextToken()));
			set_width(Integer.parseInt(tokens.nextToken()));

			for(int i=0; i<getHeight(); i++) {
                String line = in.readLine();
                for (int j = 0; j < getWidth(); j++) {
                    _map[i][j] = line.substring(j, j+1);
                }
            }
			in.close();
		} catch (IOException e) {
			throw new LoadLevelException("Error loading level ", e);
		}
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình

//
//		// thêm Bomber
//		int xBomber = 1, yBomber = 1;
//		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
//		Screen.setOffset(0, 0);
//		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));

//		// thêm Brick
//		int xB = 3, yB = 1;
//		_board.addEntity(xB + yB * _width,
////				new LayeredEntity(xB, yB,
////					new Grass(xB, yB, Sprite.grass),
////					new Brick(xB, yB, Sprite.brick)
////				)
////		);
//
//		// thêm Item kèm Brick che phủ ở trên
//		int xI = 1, yI = 2;
//		_board.addEntity(xI + yI * _width,
//				new LayeredEntity(xI, yI,
//					new Grass(xI ,yI, Sprite.grass),
//					new SpeedItem(xI, yI, Sprite.powerup_flames),
//					new Brick(xI, yI, Sprite.brick)
//				)
//		);



			for (int i = 0; i < getHeight(); i++) {
				for (int j = 0; j < getWidth(); j++) {
					addLevelEntity(_map[i][j].charAt(0), j, i);
				}
			}


	}

	public void addLevelEntity(char c, int x, int y){
	    int pos = x + y * getWidth();
	    switch(c) {
            //them Wall
            case '#':
                _board.addEntity(pos, new Wall(x, y, Sprite.wall));
                break;
            //them Bomber
            case 'p':
                _board.addCharacter(new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                Screen.setOffset(0, 0);

                _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;

            //them enermy
			case '1':
				_board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
				break;

			//them brick
            case '*':
                _board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick)));
                break;

			//them portal
			case 'x':
				_board.addEntity(x + y * _width,
						new LayeredEntity(x, y,
								//new Grass(x, y, Sprite.grass),
								new Portal(x, y, Sprite.portal),
								new Brick(x, y, Sprite.brick)
						));
				break;
            // thêm Item kèm Brick che phủ ở trên
            case 's':
                _board.addEntity(x + y * _width,
                        new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new SpeedItem(x, y, Sprite.powerup_speed),
                                new Brick(x, y, Sprite.brick)
                        ));
                break;
			case 'b':
				_board.addEntity(x + y * _width,
						new LayeredEntity(x, y,
								new Grass(x, y, Sprite.grass),
								new BombItem(x, y, Sprite.powerup_bombs),
								new Brick(x, y, Sprite.brick)
						));
				break;
			case 'f':
				_board.addEntity(x + y * _width,
						new LayeredEntity(x, y,
								new Grass(x, y, Sprite.grass),
								new FlameItem(x, y, Sprite.powerup_flames),
								new Brick(x, y, Sprite.brick)
						));
				break;
            default:
                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
        }
    }

}
