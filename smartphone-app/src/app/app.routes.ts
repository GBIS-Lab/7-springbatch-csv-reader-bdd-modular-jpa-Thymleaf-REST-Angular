import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SmartphoneListComponent } from './components/smartphone-list/smartphone-list.component';  // Import de notre composant

export const routes: Routes = [
  { path: '', component: SmartphoneListComponent },   // Par défaut, afficher la liste des smartphones
  { path: 'smartphones', component: SmartphoneListComponent },  // Route dédiée pour la liste des smartphones
  // Ajoute ici d'autres routes si nécessaire (comme /smartphone/:id pour un détail de smartphone)
];
