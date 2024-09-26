# Definir os diretórios
SRC_DIR = src
BIN_DIR = bin

# Buscar todos os arquivos .java na pasta src
SOURCES := $(wildcard $(SRC_DIR)/*.java)

# Definir o nome da classe principal
MAIN_CLASS = src.Main

# Definir o nome da aplicação
APP_NAME = ForcaJavaApp

# Regra padrão para compilar o projeto
all: compile

# Regra para compilar os arquivos Java
compile:
	@echo "Compilando arquivos .java..."
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SOURCES)

# Regra para executar o programa principal
run:
	@echo "Executando o programa..."
	java -cp $(BIN_DIR) $(MAIN_CLASS)

# Limpar arquivos compilados
clean:
	@echo "Limpando arquivos compilados..."
	rm -rf $(BIN_DIR)/*

.PHONY: all compile run clean
