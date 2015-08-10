INSERT INTO `tblmeta` VALUES (1,'colour'),(2,'favourite'),(4,'green'),(3,'red');
INSERT INTO `tblstatement` VALUES (1,'Favourite colour?'),(3,'Green'),(2,'Red');
INSERT INTO `tblstatement_tblmeta` VALUES (1,1),(1,2),(2,3),(3,4);
INSERT INTO `tblquestion` VALUES (1,'Favourite colour');
INSERT INTO `tblquestion_tblstatement` VALUES ('',1,1,0),('',3,1,1);

