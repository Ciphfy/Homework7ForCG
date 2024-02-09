//#version 120
//
// simple.frag
//
uniform mat4 mat[4];
uniform sampler2D texture0;
uniform vec3 lightpos;
uniform vec3 lightcolor;
varying vec3 normal;
varying vec4 color;
varying vec2 texcoord;
varying vec3 viewdir;
varying vec3 lightdir;

void main (void){

  vec3 diffuse = color.xyz;
  //vec3 specular = lightcolor;
  vec3 specular = vec3(0.5,0.5,0.2);
  vec3 ambient = 0.25*diffuse*lightcolor;
  vec3 bump = (texture2D(texture0, texcoord)*2.0-1.0).xyz;
  vec3 v = normalize(viewdir);
  vec3 l = normalize(lightdir);
  vec3 n = normalize(normal+0.15*bump);
  float kd = dot(n,normalize(lightpos-viewdir));


  //vec3 reflection = (0.95*max(dot(l,n),0.0))*diffuse*lightcolor
                    +ambient;
  vec3 reflection = 0.95*kd*diffuse*lightcolor
                    +ambient;
  gl_FragColor = vec4(reflection,1.0);
  //  gl_FragColor = vec4(viewdir,1.0);
}
