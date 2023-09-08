import { IArtist } from 'app/shared/model/artist.model';

export interface IArtistView {
  id?: number;
  voMember?: number | null;
  artist?: IArtist | null;
}

export const defaultValue: Readonly<IArtistView> = {};
