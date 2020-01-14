# Entity Linking

### Installazione
Il servizio di Entity Linking può essere installato usando Docker eseguendo da terminale i seguenti comandi.


```
git clone https://github.com/ecodigit/entitylinking.git
cd entitylinking/
docker build --build-arg SERVER_PORT=8080 --build-arg TAGME_KEY=<TAGME_KEY> . --no-cache -t entity_linking
```

dove ``<TAGME_KEY>`` è la chiave per accedere al servizio [TagMe](https://tagme.d4science.org/tagme/).

### Esecuzione

Una volta costuito il docker container ``entity_linking'' è possibile lanciare il server con comando.

```
docker run entity_linking
```
