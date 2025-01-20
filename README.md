# TNTEmu (Hot Potato)

## Descripción

**TNTEmu** es un plugin para Minecraft inspirado en el popular juego de "Hot Potato". Los jugadores deben pasarse la TNT, y si un jugador la mantiene cuando se acabe el tiempo, ¡la TNT explotará y quedará eliminado!

---

## Mecánica de juego principal

- Un jugador comienza con la TNT, que actúa como la "papa caliente".
- La TNT puede ser pasada a otro jugador tocándolo.
- Si un jugador mantiene la TNT cuando se acabe el temporizador, explotará y ese jugador quedará eliminado del juego.

---

## Personalización del juego

**Configuración del tiempo límite de la TNT**  
El administrador del servidor puede configurar el tiempo límite que la TNT tendrá antes de explotar.

**Ajustes del radio y daño de la explosión**  
El plugin permite personalizar el radio de la explosión y la cantidad de daño que causará.

**Mensajes de aviso y notificaciones en pantalla**  
El servidor puede enviar mensajes de advertencia a los jugadores sobre el tiempo restante o cuando la TNT esté a punto de explotar.

---

## Mapas y Arena

**Configuración de arenas prediseñadas**  
El plugin incluye un sistema para configurar arenas prediseñadas para las partidas, ofreciendo una experiencia de juego más variada.

**Protección contra destrucción del mapa con explosiones**  
Para evitar que el mapa se destruya por las explosiones, el plugin incluye medidas de protección que preservan la estructura del mundo.

---

## Sistema de rondas

**Eliminación de jugadores y reducción del área jugable con cada ronda**  
Con cada ronda, los jugadores eliminados se irán fuera del juego, y el área jugable se reducirá para hacer la partida más intensa.

**Último jugador sobreviviente gana la partida**  
El último jugador que quede con vida, sin haber sido alcanzado por la explosión, será el ganador.

---

## Extras opcionales

- **Power-ups aleatorios en el mapa**  
  Durante las partidas, pueden aparecer power-ups que otorgan ventajas a los jugadores, como velocidad extra o invulnerabilidad temporal.
  
- **Efectos especiales al pasar la TNT**  
  Se pueden activar efectos como sonidos especiales o partículas cuando un jugador pasa la TNT, añadiendo un toque visual y sonoro a la experiencia.

- **Integración de estadísticas**  
  Se pueden recopilar estadísticas del juego, como el tiempo promedio de supervivencia y los jugadores ganadores, para ofrecer un seguimiento detallado de las partidas.

---

## Requisitos

- Minecraft 1.8 o superior.
- Un servidor de Minecraft con soporte para plugins de Bukkit/Spigot.

---

## Instalación

1. Descarga el archivo `.jar` del plugin.
2. Colócalo en la carpeta `plugins` de tu servidor de Minecraft.
3. Reinicia o recarga tu servidor.
4. Configura los parámetros del plugin a través del archivo de configuración.

---

## Cosas Hechas

1. **Setup de arenas**
Sistema de creacion de arenas atraves de un palo y seleccion de lugar de spawn.
2. **Funciones Basicas**
El tema de que funcione la patata(tnt) y el momento de pasarla al jugador.
3. **Timer del Juego**
El contador basico del juego en si.

---

## Licencia

Este proyecto está licenciado bajo la Licencia MIT.

