package objects;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import game.EntityTexture;
import game.Grid;
import game.Main;
import game.ResourceManager;
import org.lwjgl.input.Mouse;

import animation.AnimationManager;
import animation.CustomAnimationValue;

/**
 * This preview is visible when in building mode. It It shows what and where will be build
 * with the next mouse click.
 * @author Benedikt Ringlein
 */

public class BuildPreview extends Entity {
	
	private boolean show = false;
	private int buildingType = 0;
	private int radius;
	private final float RADIUSMAX = 25;
	private int animRadius = -5;
	
	public int getAnimRadius() {
		return animRadius;
	}

	public void setAnimRadius(int animRadius) {
		this.animRadius = animRadius;
	}

	public int getBuildingType() {
		return buildingType;
	}

	public BuildPreview(int[] displaylist, EntityTexture texture) {
		super(displaylist, texture);
	}
	
	public BuildPreview(int bt) {
		super(Buildings.getBuildingType(bt).getDisplaylist(), Buildings.getBuildingType(bt).getTexture());
		buildingType = bt;
	}
	
	public BuildPreview() {
		super(Buildings.getBuildingType(0).getDisplaylist(), Buildings.getBuildingType(0).getTexture());
	}
	
	public void setBuilding(int bt)
	{
		if(bt==-1){
			show = false;
			buildingType = -1;
			return;
		}
		setDisplayList(Buildings.getBuildingType(bt).getDisplaylist());
		setTexture(Buildings.getBuildingType(bt).getTexture());
		show = true;
		buildingType = bt;
	}
	
	@Override
	public void setVisible(boolean visible) {
		if(!isVisible()&&visible)
		{
			radius = 0;
		AnimationManager.animateValue(null, new CustomAnimationValue(){
			@Override
			public double getValue() {
				return Main.buildpreview.radius;
			}
			@Override
			public void setValue(double input) {
				Main.buildpreview.radius = (int) input;
			}
		}, RADIUSMAX, 400);
		}
		
		super.setVisible(visible);
	}
	
	public void waveEffect()
	{
		setAnimRadius(-5);
		AnimationManager.animateValue(null, new CustomAnimationValue(){
			@Override
			public double getValue() {
				return getAnimRadius();
			}
			@Override
			public void setValue(double input) {
				setAnimRadius((int) input);
			}
		}, RADIUSMAX+5f, 500);
	}
	
	public BuildingType getBt()
	{
		return Buildings.getBuildingType(getBuildingType());
	}
	
	@Override
	public void draw() {
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);
		
		//Draw street delete preview, if needed
		try {
			if(Main.selectedTool==Main.TOOL_DELETE&&Mouse.isButtonDown(0)&&ResourceManager.getObject(Main.hoveredEntity).getBuildingType()==Buildings.BUILDINGTYPE_STREET){
				Streets.updatePreview(Math.round(Main.mousepos3d[0]), Math.round(Main.mousepos3d[2]));
				if(Streets.isVertical()){
					for(int y=Streets.getY1();y<Streets.getY2();y++){
						if(Grid.getCell(Streets.getX1(), y).getBuildingType()==Buildings.BUILDINGTYPE_STREET){
							glTranslatef(Streets.getX1(), 0.002f, y);
							glColor3f(1f, 0f, 0f);
							glCallList(ResourceManager.OBJECT_GRIDCELL[0]);
							glTranslatef(-Streets.getX1(), -0.002f, -y);
						}	
					}
				}else{
					for(int x=Streets.getX1();x<Streets.getX2();x++){
						if(Grid.getCell(x, Streets.getY1()).getBuildingType()==Buildings.BUILDINGTYPE_STREET){
							glTranslatef(x, 0.002f, Streets.getY1());
							glColor3f(1f, 0f, 0f);
							glCallList(ResourceManager.OBJECT_GRIDCELL[0]);
							glTranslatef(-x, -0.002f, -Streets.getY1());
						}
					}
				}
			}
		} catch (Exception e) {}
			
		
		float width,depth;
		
		if(!isVisible()||!show)return;
		glPushMatrix();
			//Draw grid
			glDisable(GL_TEXTURE_2D);
			glDisable(GL_DEPTH_TEST);
			int x1,x2,z1,z2;
			if(Main.buildRotation!=0&&Main.buildRotation!=180){
				x1 = (int) (getX() - (int) Math.ceil(Buildings.getBuildingType(getBuildingType()).getDepth()/2-1));
				x2 = (int) (getX() + (int) Math.floor(Buildings.getBuildingType(getBuildingType()).getDepth()/2));
				z1 = (int) (getZ() -(int) Math.ceil(Buildings.getBuildingType(getBuildingType()).getWidth()/2-1));
				z2 = (int) (getZ() +(int) Math.floor(Buildings.getBuildingType(getBuildingType()).getWidth()/2));
				width = Buildings.getBuildingType(getBuildingType()).getDepth();
				depth = Buildings.getBuildingType(getBuildingType()).getWidth();
			}else{
				x1 = (int) (getX() - (int) Math.ceil(Buildings.getBuildingType(getBuildingType()).getWidth()/2-1));
				x2 = (int) (getX() + (int) Math.floor(Buildings.getBuildingType(getBuildingType()).getWidth()/2));
				z1 = (int) (getZ() -(int) Math.ceil(Buildings.getBuildingType(getBuildingType()).getDepth()/2-1));
				z2 = (int) (getZ() +(int) Math.floor(Buildings.getBuildingType(getBuildingType()).getDepth()/2));
				width = Buildings.getBuildingType(getBuildingType()).getWidth();
				depth = Buildings.getBuildingType(getBuildingType()).getDepth();
			}
			
			float alpha=1;
			for(int z=z1-radius;z<=z2+radius;z++){
				for(int x=x1-radius;x<=x2+radius;x++){
					if(z>=z1&&z<=z2&&x>=x1&&x<=x2){
						//Color cells under the building
						try {
							if(Grid.getCell(x, z).getBuilding()!=null){
								glColor4f(1f, 0f, 0f,0.7f*(radius/RADIUSMAX));
							}
							else {
								Color c = Buildings.getBuildingType(getBuildingType()).getGridColor();
								glColor4f(c.getRed()/255, c.getGreen()/255, c.getBlue()/255,0.7f*(radius/RADIUSMAX));
							}
						} catch (Exception e) {}
					}
					else {
						//Color other cells within the radius
						if(Grid.getCell(x, z)==null)break;
						alpha = (radius-((float) Math.sqrt((getX()-x)*(getX()-x)+(getZ()-z)*(getZ()-z))))/radius;
						if(Grid.getCell(x, z).getBuilding()!=null){
							Color col = Buildings.getBuildingType(Grid.getCell(x, z).getBuildingType()).getGridColor();
							glColor4f(col.getRed(), col.getGreen(), col.getBlue(),alpha);
						}else {
							int he = Grid.getCell(x, z).getHappinessEffect();
							if(he>0){
								glColor4f(1-he/30f, 1f, 1-he/30f,alpha-((x%2==0^z%2==0)?0.1f:0f));
							}else{
								glColor4f(1f, 1-he/-30f, 1-he/-30f,alpha-((x%2==0^z%2==0)?0.1f:0f));
							}
						}
					}
					
					alpha = (float) ((alpha>0.6)?0.9:alpha+0.3);
					if(alpha>0)
					{
						if(Grid.getCell(x, z)!=null&&Grid.getCell(x, z).getBuilding()==null)
						{
							float dist = (float) Math.sqrt((getX()-x)*(getX()-x)+(getZ()-z)*(getZ()-z));
							glTranslatef(x+(1-alpha)*((x-getX())/RADIUSMAX), 
									0.001f+((Math.abs(dist-animRadius)<=3)?3-Math.abs(dist-animRadius):0), 
									z+(1-alpha)*((z-getZ())/RADIUSMAX));
							glScalef((alpha>0)?alpha:1, 1, (alpha>0)?alpha:1);
							glRotatef(15*(1-alpha), 0, 1, 0);
							glCallList(ResourceManager.OBJECT_GRIDCELL[0]);
							glRotatef(-15*(1-alpha), 0, 1, 0);
							glScalef((alpha>0)?(1f/alpha):1, 1, (alpha>0)?(1f/alpha):1);
							glTranslatef(-x-(1-alpha)*((x-getX())/RADIUSMAX),
									-0.001f-((Math.abs(dist-animRadius)<=3)?3-Math.abs(dist-animRadius):0), 
									-z-(1-alpha)*((z-getZ())/RADIUSMAX));
						}else  {
							glTranslatef(x, 0.001f, z);
							glCallList(ResourceManager.OBJECT_GRIDCELL[0]);
							glTranslatef(-x, -0.001f, -z);
						}
					}
				}
			}
			
			//Draw street preview, if needed
			if(Main.selectedTool==Main.TOOL_ADD &&Main.currentBT==Buildings.BUILDINGTYPE_STREET&&Mouse.isButtonDown(0)){
				Streets.updatePreview(Math.round(Main.mousepos3d[0]), Math.round(Main.mousepos3d[2]));
				if(Streets.isVertical()){
					for(int y=Streets.getY1();y<Streets.getY2();y++){
						glTranslatef(Streets.getX1(), 0.002f, y);
						if(Grid.isAreaFree(Streets.getX1(), y, 1, 1))glColor3f(0.8f, 0.5f, 0f);
						else glColor3f(1f, 0.8f, 0f);
						glCallList(ResourceManager.OBJECT_GRIDCELL[0]);
						glTranslatef(-Streets.getX1(), -0.002f, -y);
					}
				}else{
					for(int x=Streets.getX1();x<Streets.getX2();x++){
						glTranslatef(x, 0.002f, Streets.getY1());
						if(Grid.isAreaFree(x, Streets.getY1(), 1, 1))glColor3f(0.8f, 0.5f, 0f);
						else glColor3f(1f, 0.8f, 0f);
						glCallList(ResourceManager.OBJECT_GRIDCELL[0]);
						glTranslatef(-x, -0.002f, -Streets.getY1());
					}
				}
			}
			
			//Draw happinessEffect influence, if needed
			if(getBt().getHappinessEffect()!=0
					&&getBt().getHappinessRadius()>0)
			{
				glDisable(GL_DEPTH_TEST);
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, ResourceManager.TEXTURE_HAPPINESSEFFECT.getTextureID());
				int he = getBt().getHappinessEffect();
				if(he>0){
					glColor4f(1-he/30f, 1f, 1-he/30f,1);
				}else{
					glColor4f(1f, 1-he/-30f, 1-he/-30f,1);
				}
				glTranslatef(getX(), 0.01f, getZ());
				float r = (radius<getBt().getHappinessRadius())?radius:getBt().getHappinessRadius();
				glScalef(2*r, 1, 2*r);
				glCallList(ResourceManager.OBJECT_STREET[0]);
				glScalef(0.5f/r, 1, 0.5f/r);
				glTranslatef(-getX(), 0.01f, -getZ());
			}
			
			//Draw producedSupplyRadius, if needed
			if(getBt().getProducedSupplyRadius()!=0)
			{
				glDisable(GL_DEPTH_TEST);
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, ResourceManager.TEXTURE_HAPPINESSEFFECT.getTextureID());
				glColor4f(0.8f,0.8f,0,radius/RADIUSMAX);
				glTranslatef(getX(), 0.5f+(float)(0.5*Math.sin((System.currentTimeMillis()%3141)*0.002)), getZ());
				float r = getBt().getProducedSupplyRadius();
				glScalef(2*r, 1, 2*r);
				glCallList(ResourceManager.OBJECT_STREET[0]);
				glScalef(0.5f/r, 1, 0.5f/r);
				glTranslatef(-getX(), -0.5f-(float)(0.5*Math.sin((System.currentTimeMillis()%3141)*0.002)), -getZ());
			}
			
			//Draw producesSupplyRadius of other buildings that produce the same supply
			if(getBt().getProducedSupplyAmount()>0&&getBt().getProducedSupplyRadius()>0)
			{
				glDisable(GL_DEPTH_TEST);
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, ResourceManager.TEXTURE_SUPPLYRADIUS.getTextureID());
				glColor4f(0.8f,0.8f,0,radius*0.5f/RADIUSMAX);
				for(Building building:Buildings.buildings)
				{
					if(building.getProducedSupply()==getBt().getProducedSupply())
					{
						glTranslatef(building.getX(), 0.01f, building.getZ());
						float r = building.getProducedSupplyRadius();
						glScalef(2*r, 1, 2*r);
						glCallList(ResourceManager.OBJECT_STREET[0]);
						glScalef(0.5f/r, 1, 0.5f/r);
						glTranslatef(-building.getX(), -0.01f, -building.getZ());
					}
				}
			}
			
			//Draw buildradius
			glDisable(GL_DEPTH_TEST);
			glEnable(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, ResourceManager.TEXTURE_HAPPINESSEFFECT.getTextureID());
			glColor4f(0.2f,0.3f,1,radius*0.5f/RADIUSMAX);
			glScalef(Main.BuildingCitycenter.getBuildRadius()*2, 1, Main.BuildingCitycenter.getBuildRadius()*2);
			glCallList(ResourceManager.OBJECT_STREET[0]);
			glScalef(0.5f/Main.BuildingCitycenter.getBuildRadius(), 1, 0.5f/Main.BuildingCitycenter.getBuildRadius());
			
			
			//Draw building
			glEnable(GL_DEPTH_TEST);
			glTranslatef(getX()-((width%2==1)?0.5f:0), 0.25f+(float)(0.25*Math.cos((System.currentTimeMillis()%3141)*0.002)), getZ()-((depth%2==1)?0.5f:0));
			glScalef(getScaleX(), getScaleY(), getScaleZ());
			glRotatef(getRotX(), 1, 0, 0);
			glRotatef(getRotY()+Main.buildRotation, 0, 1, 0);
			glRotatef(getRotZ(), 0, 0, 1);
			glEnable(GL_TEXTURE_2D);
			boolean areafree=false;
			if(Main.buildRotation!=0&&Main.buildRotation!=180)
			{
				areafree=Grid.isAreaFree((int)getX(), (int)getZ(), Buildings.getBuildingType(buildingType).getDepth(), Buildings.getBuildingType(buildingType).getWidth());
			}else{
				areafree=Grid.isAreaFree((int)getX(), (int)getZ(), Buildings.getBuildingType(buildingType).getWidth(), Buildings.getBuildingType(buildingType).getDepth());
			}
			if(areafree&&(Grid.buildingSurroundedWith((int) Math.round(Main.mousepos3d[0]), (int) Math.round(Main.mousepos3d[2]), Main.currentBT, Buildings.BUILDINGTYPE_STREET,Main.buildRotation))||Main.currentBT==Buildings.BUILDINGTYPE_STREET){
				glColor4f(1f, 1f, 1f, 1f);
				ResourceManager.drawEntity(this);
			}
			glPopMatrix();
			glColor4f(1f, 1f, 1f, 1f);
			glEnable(GL_LIGHTING);
			
	}

}
