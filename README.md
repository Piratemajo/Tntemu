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

- **Música personalizada**  
  Se puede configurar música personalizada para que suene durante las partidas, incluyendo soporte para múltiples pistas.

- **Placeholders personalizados**  
  Se pueden usar placeholders personalizados para mostrar estadísticas de los jugadores en hologramas o mensajes.

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
   - Sistema de creación de arenas a través de un palo para seleccionar las esquinas y el punto de reaparición.
   - Añadido comando `/tntemu setup <nombre>` para configurar arenas.
   - Añadido comando `/tntemu select <nombre>` para seleccionar una arena.
   - Añadido comando `/tntemu list` para listar las arenas disponibles.

2. **Funciones Básicas**
   - Implementación de la mecánica de la patata caliente (TNT).
   - Añadido método `giveTNT(Player player)` para dar la TNT a un jugador.
   - Añadido método `explodePlayer(Player player)` para manejar la explosión de un jugador.
   - Añadido método `generarParticulas(Player jugador)` para generar partículas al pasar la TNT.

3. **Timer del Juego**
   - Implementación del temporizador del juego con BossBar.
   - Añadido método `startGame(List<Player> players)` para iniciar el juego.
   - Añadido método `endGame()` para finalizar el juego.

4. **Menú de Administración**
   - Añadido menú de administración para manejar el plugin.
   - Añadido comando `/tntemu menu admin` para abrir el menú de administración.
   - Implementación de eventos para manejar clics en el menú de administración.

5. **Placeholders Personalizados**
   - Añadido soporte para placeholders personalizados para mostrar estadísticas de los jugadores.

---

## Licencia

Este proyecto está licenciado bajo la Licencia MIT.

---

## Soporte para folia

Este proyecto en algun momento tendra soporte para folia.

---

