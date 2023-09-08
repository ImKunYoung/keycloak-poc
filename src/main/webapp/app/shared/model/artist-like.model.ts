import { IArtist } from 'app/shared/model/artist.model';

export interface IArtistLike {
  id?: number;
  voMember?: number | null;
  artist?: IArtist | null;
}

export const defaultValue: Readonly<IArtistLike> = {};
