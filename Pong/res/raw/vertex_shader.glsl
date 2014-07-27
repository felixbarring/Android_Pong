
uniform mat4 u_Matrix;

attribute vec4 a_Position; 
uniform vec4 u_offset;

void main()                    
{                              
    gl_Position = u_Matrix * (a_Position + u_offset); 
}   
