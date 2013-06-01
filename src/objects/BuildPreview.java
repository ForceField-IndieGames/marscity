package objects;

import static org.lwjgl.opengl.GL11.*;
import game.Grid;
import game.Main;
import game.ResourceManager;


import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

/**
 * This preview is visible when in building mode. It It shows what and where will be build
 * with the next mouse click.
 * @author Benedikt Ringlein
 */

public class BuildPreview extends Entity {
	
	private boolean show = false;
	private int buildingType = 0;

	
	public int getBuildingType() {
		return buildingType;
	}

	public BuildPreview(int displaylist, Texture texture) {
		super(displaylist, texture);
	}
	
	public BuildPreview(int bt) {
		super(ResourceManager.getBuildingType(bt).getDisplaylist(), ResourceManager.getBuildingType(bt).getTexture());
		buildingType = bt;
	}
	
	public BuildPreview() {
		super(ResourceManager.getBuildingType(0).getDisplaylist(), ResourceManager.getBuildingType(0).getTexture());
	}
	
	public void setBuilding(int bt)
	{
		if(bt==-1){
			show = false;
			buildingType = -1;
			return;
		}
		setDisplayList(ResourceManager.getBuildingType(bt).getDisplaylist());
		setTexture(ResourceManager.getBuildingType(bt).getTexture());
		show = true;
		buildingType = bt;
	}
	
	@Override
	public void draw() {
		if(!isVisible()||!show)return;
		glPushMatrix();
			glDisable(GL_LIGHTING);
			
			
			//Draw grid
			glDisable(GL_TEXTURE_2D);
			glDisable(GL_DEPTH_TEST);
			int x1 = (int) (getX() - (int) Math.ceil(ResourceManager.getBuildingType(getBuildingType()).getWidth()/2-1));
			int x2 = (int) (getX() + (int) Math.floor(ResourceManager.getBuildingType(getBuildingType()).getWidth()/2));
			int z1 = (int) (getZ() -(int) Math.ceil(ResourceManager.getBuildingType(getBuildingType()).getDepth()/2-1));
			int z2 = (int) (getZ() +(int) Math.floor(ResourceManager.getBuildingType(getBuildingType()).getDepth()/2));
			int radius = 50;
			for(int z=z1-radius;z<=z2+radius;z++){
				for(int x=x1-radius;x<=x2+radius;x++){
					if(z>=z1&&z<=z2&&x>=x1&&x<=x2){
						//Color cells under the building
						if(Grid.getCell(x, z).getBuilding()!=null)glColor4f(1f, 0f, 0f,0.7f);
						else glColor4f(0f, 1f, 0f,0.7f);
					}
					else {
						//Color other cells within the radius
						if(Grid.getCell(x, z)==null)break;
						float alpha = (radius-((float) Math.sqrt((getX()-x)*(getX()-x)+(getZ()-z)*(getZ()-z))))/radius-((x%2==0^z%2==0)?0.1f:0f);
						if(Grid.getCell(x, z).getBuilding()!=null){
							if(Grid.getCell(x, z).getBuilding().getBuidlingType()==ResourceManager.BUILDINGTYPE_STREET)glColor4f(0.8f, 0.8f, 0.8f,alpha);
							else glColor4f(0.5f, 0.5f, 0f,alpha);
						}else glColor4f(1f, 1f, 1f,alpha);
					}
					glTranslatef(x, 0.001f, z);
					glCallList(ResourceManager.OBJECT_GRIDCELL);
					glTranslatef(-x, -0.001f, -z);
				}
			}
			
			//Draw street prewiev, if needed
			if(Main.selectedTool==Main.TOOL_ADD &&Main.currentBuildingType==ResourceManager.BUILDINGTYPE_STREET&&Mouse.isButtonDown(0)){
				Streets.updateBuilding(Math.round(Main.mousepos3d[0]), Math.round(Main.mousepos3d[2]));
				if(Streets.isVertical()){
					for(int y=Streets.getY1();y<Streets.getY2();y++){
						glTranslatef(Streets.getX1(), 0.002f, y);
						if(Grid.isAreaFree(Streets.getX1(), y, 1, 1))glColor3f(0.8f, 0.5f, 0f);
						else glColor3f(1f, 0.8f, 0f);
						glCallList(ResourceManager.OBJECT_GRIDCELL);
						glTranslatef(-Streets.getX1(), -0.002f, -y);
					}
				}else{
					for(int x=Streets.getX1();x<Streets.getX2();x++){
						glTranslatef(x, 0.002f, Streets.getY1());
						if(Grid.isAreaFree(x, Streets.getY1(), 1, 1))glColor3f(0.8f, 0.5f, 0f);
						else glColor3f(1f, 0.8f, 0f);
						glCallList(ResourceManager.OBJECT_GRIDCELL);
						glTranslatef(-x, -0.002f, -Streets.getY1());
					}
				}
				
			}
			
			//Draw building
			glEnable(GL_DEPTH_TEST);
			glTranslatef(getX(), 0, getZ());
			glScalef(getScaleX(), getScaleY(), getScaleZ());
			glRotatef(getRotX(), 1, 0, 0);
			glRotatef(getRotY(), 0, 1, 0);
			glRotatef(getRotZ(), 0, 0, 1);
			glEnable(GL_TEXTURE_2D);
			if(getTexture()!=null)glBindTexture(GL_TEXTURE_2D, getTexture().getTextureID());
			else glBindTexture(GL_TEXTURE_2D, 0);
			if(Grid.isAreaFree((int)getX(), (int)getZ(), ResourceManager.getBuildingType(buildingType).getWidth(), ResourceManager.getBuildingType(buildingType).getDepth())){
				glColor4f(1f, 1f, 1f, 1f);
				glCallList(getDisplayList());
			}
			
			
			glPopMatrix();
			
			
			
			glColor4f(1f, 1f, 1f, 1f);
			glEnable(GL_LIGHTING);
	}

}
