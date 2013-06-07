package objects;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mesh {

    private final List<Vector3f> vertices = new ArrayList<Vector3f>();
    private final List<Vector2f> textureCoordinates = new ArrayList<Vector2f>();
    private final List<Vector3f> normals = new ArrayList<Vector3f>();
    private final List<Face> faces = new ArrayList<Face>();
    private final HashMap<String, Material> materials = new HashMap<String, Material>();
    private boolean enableSmoothShading = true;
    
    public Mesh()
    {
    	
    }

    public boolean hasTextureCoordinates() {
        return getTextureCoordinates().size() > 0;
    }

    public boolean hasNormals() {
        return getNormals().size() > 0;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector2f> getTextureCoordinates() {
        return textureCoordinates;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public boolean isSmoothShadingEnabled() {
        return enableSmoothShading;
    }

    public void setSmoothShadingEnabled(boolean smoothShadingEnabled) {
        this.enableSmoothShading = smoothShadingEnabled;
    }

    public HashMap<String, Material> getMaterials() {
        return materials;
    }

    public static class Material {

        public float specular = 100;
        public float[] ambientCol = {0.2f, 0.2f, 0.2f};
        public float[] diffuseCol = {1f, 1, 1};
        public float[] specularCol = {1, 1, 1};
        public Texture texture;
    }

    public static class Face {

        private final int[] vertexIndex = {-1, -1, -1};
        private final int[] normalIndex = {-1, -1, -1};
        private final int[] textureCoordinateIndex = {-1, -1, -1};
        private Material material;

        public Face(int[] vertexIndices) {
		    this.vertexIndex[0] = vertexIndices[0];
		    this.vertexIndex[1] = vertexIndices[1];
		    this.vertexIndex[2] = vertexIndices[2];
		}

		public Face(int[] vertexIndices, int[] normalIndices) {
		    this.vertexIndex[0] = vertexIndices[0];
		    this.vertexIndex[1] = vertexIndices[1];
		    this.vertexIndex[2] = vertexIndices[2];
		    this.normalIndex[0] = normalIndices[0];
		    this.normalIndex[1] = normalIndices[1];
		    this.normalIndex[2] = normalIndices[2];
		}

		public Face(int[] vertexIndices, int[] normalIndices, int[] textureCoordinateIndices, Material material) {
		    this.vertexIndex[0] = vertexIndices[0];
		    this.vertexIndex[1] = vertexIndices[1];
		    this.vertexIndex[2] = vertexIndices[2];
		    this.textureCoordinateIndex[0] = textureCoordinateIndices[0];
		    this.textureCoordinateIndex[1] = textureCoordinateIndices[1];
		    this.textureCoordinateIndex[2] = textureCoordinateIndices[2];
		    this.normalIndex[0] = normalIndices[0];
		    this.normalIndex[1] = normalIndices[1];
		    this.normalIndex[2] = normalIndices[2];
		    this.material = material;
		}

		public Material getMaterial() {
            return material;
        }

        public boolean hasNormals() {
            return normalIndex[0] != -1;
        }

        public boolean hasTextureCoordinates() {
            return textureCoordinateIndex[0] != -1;
        }

        public int[] getVertexIndices() {
            return vertexIndex;
        }

        public int[] getTextureCoordinateIndices() {
            return textureCoordinateIndex;
        }

        public int[] getNormalIndices() {
            return normalIndex;
        }
    }
}