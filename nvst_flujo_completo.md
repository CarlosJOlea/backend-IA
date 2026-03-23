# 📘 Flujo Completo NVST — Spring Boot Hexagonal Template

Este documento describe paso a paso el flujo seguido para generar un template backend con arquitectura hexagonal y vertical slice utilizando NVST + agentes IA.

---

# 🎯 Objetivo

Construir un template reusable de backend con:

- Spring Boot  
- Arquitectura hexagonal  
- Vertical slicing  
- CRUD completo funcional  

---

# 🧱 1. Setup inicial

## Instalación de Bun

```bash
irm bun.sh/install.ps1 | iex
bun --version
```

## Instalación de dependencias

```bash
bun install
```

## Build del binario

```bash
bun run build:binary
```

## Verificar CLI

```bash
./dist/nvst.exe --help
```

---

# ⚙️ 2. Configuración global del CLI

```bash
copy dist/nvst.exe ~/.bun/bin/nvst
```

Uso global:

```bash
nvst --help
```

---

# 🚀 3. Inicialización del proyecto

```bash
mkdir nvst-prueba
cd nvst-prueba
nvst init
```

---

# 🔁 4. Inicio de iteración

```bash
nvst start iteration
```

---

# 🧠 5. Definición de requerimientos (PRD)

```bash
nvst define requirement --agent claude
```

---

# 🧱 8. Generación del prototype

```bash
git init
git add .
git commit -m "init"

nvst create prototype --agent claude
```

---

# 🔍 11. Auditoría

```bash
nvst audit prototype --agent claude
```

---

# 🏁 12. Cierre del flujo

Seleccionado:

```text
c
```

---

# 🔄 13. Manejo de estado y sincronización

```bash
git add .
git commit -m "chore: sync state"

nvst audit prototype --agent claude
nvst refactor prototype --agent claude
```

---

# ✅ 14. Aprobación final

```bash
nvst approve prototype
```

---

# 💾 15. Commit final

```bash
git add .
git commit -m "feat: complete iteration it_000002"
```

---

# 🧠 Métricas del experimento

- 💰 Tokens iniciales: $5.00 USD  
- 💰 Tokens finales: $1.90 USD  
- ⏱️ Duración: ~1 hora  
- 🔒 Entorno: laboratorio cerrado  

---

# 🚀 Resultado final

✔ Backend funcional  
✔ CRUD completo  
✔ Arquitectura limpia  
✔ Template reusable  

---

**Fin del documento** 🚀
