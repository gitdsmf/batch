-- Table 'kourel'

DROP TABLE  IF EXISTS KOUREL;
CREATE TABLE KOUREL  (
    ID_KOUREL bigint(20) NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255),
    PRIMARY KEY (ID_KOUREL)
)ENGINE=InnoDB;

 -- Insertion des données de 'kourel'
 
INSERT INTO `kourel` (`ID_KOUREL`, `nom`) VALUES ('1', 'DSMF');
INSERT INTO `kourel` (`ID_KOUREL`, `nom`) VALUES ('2', 'KHELCOM');
INSERT INTO `kourel` (`ID_KOUREL`, `nom`) VALUES ('3', 'TKSTB');

-- Table 'ndigueul'

DROP TABLE  IF EXISTS ndigueul;
CREATE TABLE ndigueul  (
    id_ndigueul bigint(20) NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255),
    PRIMARY KEY (id_ndigueul)
)ENGINE=InnoDB;

 -- Insertion des données de 'kourel'
 
INSERT INTO `ndigueul` (`id_ndigueul`, `nom`) VALUES ('1', 'MAGAL');
INSERT INTO `ndigueul` (`id_ndigueul`, `nom`) VALUES ('2', 'GAMOU');
INSERT INTO `ndigueul` (`id_ndigueul`, `nom`) VALUES ('3', 'KHASSIDA');
INSERT INTO `ndigueul` (`id_ndigueul`, `nom`) VALUES ('4', '17 AVRIL');

-- Table 'zone'

DROP TABLE  IF EXISTS ZONE;
CREATE TABLE ZONE  (
    ID_ZONE bigint(20) NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255),
    kourel bigint(20) NOT NULL,
    PRIMARY KEY (ID_ZONE)
)ENGINE=InnoDB;
ALTER TABLE zone
ADD CONSTRAINT fk_kourel_zone FOREIGN KEY (kourel) REFERENCES kourel(id_kourel);
ALTER TABLE zone ADD UNIQUE KEY `zone_key` (`nom`, `kourel`);

-- Table 'DAARA'
DROP TABLE  IF EXISTS DAARA;
CREATE TABLE DAARA  (
    ID_DAARA bigint(20) NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255),
    pays VARCHAR(255),
    zone bigint(20) NOT NULL,
    PRIMARY KEY (ID_DAARA)
)ENGINE=InnoDB;
ALTER TABLE DAARA
ADD CONSTRAINT fk_zone_daara FOREIGN KEY (zone) REFERENCES zone(id_zone);
ALTER TABLE DAARA ADD UNIQUE KEY `daara_key` (`nom`, `pays`, `zone`);

-- Table 'talibe'

DROP TABLE  IF EXISTS TALIBE;
CREATE TABLE TALIBE  (
    ID_TALIBE bigint(20) NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255)NOT NULL,
    prenom VARCHAR(255)NOT NULL,
    email VARCHAR(255)NOT NULL,
  	is_dieuwrigne_daara bit(1) NOT NULL,
  	is_percepteur bit(1) NOT NULL,
    daara bigint(20) NOT NULL,
    sass double,
    realmagal double,
    PRIMARY KEY (ID_TALIBE)
)ENGINE=InnoDB;
ALTER TABLE TALIBE
ADD CONSTRAINT fk_DAARA_TALIBE FOREIGN KEY (daara) REFERENCES daara(id_daara);
ALTER TABLE TALIBE ADD UNIQUE KEY `talibe_key` (`email`);


-- Table 'utilisateur'

DROP TABLE  IF EXISTS UTILISATEUR;
CREATE TABLE UTILISATEUR  (
    ID_UTILISATEUR bigint(20) NOT NULL,
    USERNAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255),
    ROLE VARCHAR(255) NOT NULL,
    COMPTE_ACTIVE bit(1) NOT NULL DEFAULT 0,
    ID_TALIBE bigint(20) NOT NULL,
    PRIMARY KEY (ID_UTILISATEUR)
)ENGINE=InnoDB;
ALTER TABLE UTILISATEUR
ADD CONSTRAINT fk_talibe_utilisateur FOREIGN KEY (id_talibe) REFERENCES talibe(id_talibe);
ALTER TABLE UTILISATEUR ADD UNIQUE KEY `utilisateur_key` (`USERNAME`, `PASSWORD`, `ROLE`);

-- Table SASS
DROP TABLE  IF EXISTS SASS;
CREATE TABLE SASS  (
    id_sass bigint(20) NOT NULL AUTO_INCREMENT,
    annee_evenement VARCHAR(255)NOT NULL,
    devise VARCHAR(255),
    montant_sass double ,
    ndigueul bigint(20) NOT NULL,
    talibe bigint(20) NOT NULL,
    
    PRIMARY KEY (ID_SASS)
)ENGINE=InnoDB;
ALTER TABLE SASS
ADD CONSTRAINT fk_TALIBE_SASS FOREIGN KEY (talibe) REFERENCES talibe(id_talibe);
ALTER TABLE SASS
ADD CONSTRAINT fk_ndigueul_SASS FOREIGN KEY (ndigueul) REFERENCES ndigueul(id_ndigueul);

-- Table JEUFF
DROP TABLE  IF EXISTS jeuff;
CREATE TABLE jeuff  (
    id_jeuff bigint(20) NOT NULL AUTO_INCREMENT,
    annee_evenement VARCHAR(255)NOT NULL,
    date_jeuff date,
    devise VARCHAR(255),
    etat_validation_jeuff VARCHAR(255),
    mode_envoi VARCHAR(255),
    montant_jeuff double ,
    ndigueul bigint(20) NOT NULL,
    percepteur bigint(20) NOT NULL,
    talibe bigint(20) NOT NULL,
    
    PRIMARY KEY (id_jeuff)
)ENGINE=InnoDB;
ALTER TABLE JEUFF
ADD CONSTRAINT fk_TALIBE_JEUFF FOREIGN KEY (talibe) REFERENCES talibe(id_talibe);
ALTER TABLE JEUFF
ADD CONSTRAINT fk_percepteur_JEUFF FOREIGN KEY (percepteur) REFERENCES talibe(id_talibe);
ALTER TABLE JEUFF
ADD CONSTRAINT fk_ndigueul_JEUFF FOREIGN KEY (ndigueul) REFERENCES ndigueul(id_ndigueul);

SET SESSION sql_mode='NO_AUTO_VALUE_ON_ZERO';
