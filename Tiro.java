package asteroids;

import java.util.ArrayList;
import java.util.List;


import jplay.Physics;
import jplay.Sprite;
import jplay.Window;


public class Tiro {
	private List<Sprite> disparos = new ArrayList<>();
	private List<Float> direcao = new ArrayList<>();
	
	public void inicializa_tiro(Sprite player, Physics fisica) {
		disparos.add(new Sprite("Images/tiro.png"));
		direcao.add(player.getBody().getAngle());
		disparos.get(disparos.size()-1).setX(player.x + player.width/2 -disparos.get(disparos.size()-1).width/2);  
		disparos.get(disparos.size()-1).setY(player.y + player.height/2);
		disparos.get(disparos.size()-1).setRotation(player.getBody().getAngle());
	}
	
	public void desenha_tiro(Sprite player) {
		for(Sprite disparo: disparos) {
			disparo.draw();
		}
	}
	
	public void upadate_tiro(Window janela, Asteroides asteroides, int[] Score) {
		for(int i = 0; i < disparos.size();i++) {
			if (disparos.get(i).y < -disparos.get(i).height || 
				disparos.get(i).y > janela.getHeight()+disparos.get(i).height ||
				disparos.get(i).x < -disparos.get(i).width ||
				disparos.get(i).x > janela.getWidth()+disparos.get(i).width) {
				disparos.remove(i);
				direcao.remove(i);
			}
			else if (asteroides.update(disparos.get(i))) {
				disparos.remove(i);
				direcao.remove(i);
				Score[0] += 50;
			}
			else {
				disparos.get(i).x += 10*Math.cos(direcao.get(i));
				disparos.get(i).y += -10*Math.sin(direcao.get(i));
			}
		}
	}
}
