# Minicopilador Pascal 🛠️

Un compilador sencillo de Pascal escrito en **Java**, utilizando **ANTLR 4** para generar el analizador léxico y sintáctico, y generando código intermedio en **LLVM**.

## 📁 Estructura del proyecto

```text
.
├── Interpreter 1.iml
├── README.md
├── antlr-4.13.2-complete.jar
├── lib/                      # Librerías externas
├── out/                      # Código compilado (.class)
│   ├── Home*.class
│   ├── Interpreter/          # Clases compiladas del parser y visitor ANTLR
│   ├── Main.class
│   └── RepresentacionIntermedia/  # Código compilado para LLVM y código intermedio
├── programa/                 # Archivos binarios o ejecutables
├── salida.bc                 # Archivo LLVM bitcode
├── salida.ll                 # Archivo LLVM textual
├── src/                      # Código fuente (.java, gramática, formularios)
│   ├── Home.form
│   ├── Home.java
│   ├── Interpreter/          # Código fuente parser, visitor, listener, símbolos, etc.
│   ├── Interpreter.g4        # Gramática ANTLR
│   ├── Main.java
│   └── RepresentacionIntermedia/  # Código fuente LLVM y código intermedio
└── tests/                    # Archivos de prueba (.pas)
    ├── test*.pas
    ├── testIdenScope.pas
    ├── testReadWrite.pas
    ├── testScopeVisibility.pas
    ├── testStrongTyping.pas
    └── write.pas
```

---

## ⚙️ Requisitos

- Java 17 o superior
- ANTLR 4.13.2 (incluido en `antlr-4.13.2-complete.jar`)
- LLVM (opcional, para compilar `.ll` a binario):
  ```bash
  clang salida.ll -o salida
  ```

---

## 🚀 Cómo compilar y ejecutar

Utilizar Intellij Idea para copilar y ejecutar

Esto generará `salida.ll` y `salida.bc`.

### 4. (Opcional) Generar ejecutable LLVM

```bash
clang salida.ll -o programa
./programa
```

---

## 📘 Ejemplo de uso

```pascal
program test1;
var
  x, y, z: integer;
begin
  x := 5;
  y := 10;
  z := x + y * 2;
  write(z);
end.
```

Al compilarlo, obtén `salida.ll`, luego:

```bash
clang salida.ll -o programa
./programa
```

Salida esperada: `25`

---

## 👨‍👩‍👧‍👦 Colaboradores


| Nombre          |
| --------------- |
| JRafaelLobo     |
| Jorge López    |
| Oliver IrahetaX |

---