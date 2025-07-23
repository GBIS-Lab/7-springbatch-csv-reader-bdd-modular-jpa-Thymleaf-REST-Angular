import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SmartphoneService } from '../../services/smartphone.service';
import { Smartphone } from '../../models/smartphone.model';

@Component({
  selector: 'app-smartphone-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './smartphone-list.component.html',
  styleUrls: ['./smartphone-list.component.scss']
})
export class SmartphoneListComponent implements OnInit {
  smartphones: Smartphone[] = [];

  constructor(private smartphoneService: SmartphoneService) {}

  ngOnInit(): void {
    this.smartphoneService.getSmartphones().subscribe(
      (data) => {
        this.smartphones = data;
        console.log(data);  // Vérifier les données reçues
        },
      (error) => {
        console.error('Erreur lors du chargement des smartphones', error)
        }
    );
  }
}

